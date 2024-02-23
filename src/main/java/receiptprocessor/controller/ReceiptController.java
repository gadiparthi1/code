package receiptprocessor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import receiptprocessor.models.Item;
import receiptprocessor.models.Receipt;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/receipts")
@CrossOrigin(origins = "*")
public class ReceiptController {

	private Map<String, Receipt> receiptMap = new HashMap<>();

	@PostMapping("/process")
	public ResponseEntity<Map<String, String>> processReceipt(@RequestBody Receipt receipt) {
		String receiptId = UUID.randomUUID().toString();
		receiptMap.put(receiptId, receipt);
		Map<String, String> response = new HashMap<>();
		response.put("id", receiptId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}/points")
	public ResponseEntity<Map<String, Integer>> getPoints(@PathVariable String id) {
		Receipt receipt = receiptMap.get(id);
		if (receipt == null) {
			return ResponseEntity.notFound().build();
		}
		int points = calculatePoints(receipt);
		Map<String, Integer> response = new HashMap<>();
		response.put("points", points);
		return ResponseEntity.ok(response);
	}

	private int calculatePoints(Receipt receipt) {
	    String retailerName = receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", ""); // Remove non-alphanumeric characters
		int points = retailerName.length(); // One point for every alphanumeric character in the retailer name
		points += isRoundDollarAmount(receipt.getTotal()) ? 50 : 0; // 50 points if the total is a round dollar amount
		points += isMultipleOfQuarter(receipt.getTotal()) ? 25 : 0; // 25 points if the total is a multiple of 0.25
		points += receipt.getItems().size() / 2 * 5; // 5 points for every two items on the receipt
		for (Item item : receipt.getItems()) {
			int descriptionLength = item.getShortDescription().trim().length();
			if (descriptionLength % 3 == 0) {
				double price = Double.parseDouble(item.getPrice());
				points += Math.ceil(price * 0.2); // If the trimmed length of the item description is a multiple of 3
			}
		}
		// 6 points if the day in the purchase date is odd
		points += Integer.parseInt(receipt.getPurchaseDate().substring(8)) % 2 == 1 ? 6 : 0;
		// 10 points if the time of purchase is after 2:00pm and before 4:00pm
		int purchaseHour = Integer.parseInt(receipt.getPurchaseTime().substring(0, 2));
		points += (purchaseHour >= 14 && purchaseHour < 16) ? 10 : 0;
		return points;
	}

	private boolean isRoundDollarAmount(String total) {
		double amount = Double.parseDouble(total);
		return amount == Math.round(amount);
	}

	private boolean isMultipleOfQuarter(String total) {
		double amount = Double.parseDouble(total);
		return (amount * 100) % 25 == 0;
	}
}
