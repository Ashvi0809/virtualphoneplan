package virtualPlans.AccProject.controller;

import virtualPlans.AccProject.model.dataSortingModel;
import virtualPlans.AccProject.service.dataSortingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200") // Enable CORS for this controller
@RequestMapping("/api/plans")
public class dataSortingController {

    @Autowired
    private dataSortingService sortingService;

    // Endpoint to get sorted plans by price (cheapest first)
    @GetMapping("/sort-by-price")
    public List<dataSortingModel> getSortedByPrice() {
        List<dataSortingModel> plans = sortingService.loadCSVData();
        sortingService.bubbleSortByPrice(plans);
        return plans;
    }

    // Endpoint to get sorted plans by most expensive first
    @GetMapping("/sort-by-price-desc")
    public List<dataSortingModel> getSortedByPriceDescending() {
        List<dataSortingModel> plans = sortingService.loadCSVData();
        sortingService.selectionSortByPriceDescending(plans);
        return plans;
    }

    // Endpoint to get sorted plans by company name
    @GetMapping("/sort-by-company")
    public List<dataSortingModel> getSortedByCompanyName() {
        List<dataSortingModel> plans = sortingService.loadCSVData();
        sortingService.mergeSortByCompanyName(plans, 0, plans.size() - 1);
        return plans;
    }

    // Endpoint to get sorted plans by billing cycle
    @GetMapping("/sort-by-billing-cycle")
    public List<dataSortingModel> getSortedByBillingCycle() {
        List<dataSortingModel> plans = sortingService.loadCSVData();
        sortingService.quickSortByBillingCycle(plans, 0, plans.size() - 1);
        return plans;
    }
}
