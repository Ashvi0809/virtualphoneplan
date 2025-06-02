package virtualPlans.AccProject.service;

import virtualPlans.AccProject.model.dataSortingModel;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class dataSortingService {

    private static final String FILE_PATH = "crawled-db/project.csv"; // Static file path

    // Bubble Sort by price (cheapest first)
    public void bubbleSortByPrice(List<dataSortingModel> plans) {
        int n = plans.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (plans.get(j).getPrice() > plans.get(j + 1).getPrice()) {
                    Collections.swap(plans, j, j + 1);
                }
            }
        }
    }

    // Selection Sort by price (most expensive first)
    public void selectionSortByPriceDescending(List<dataSortingModel> plans) {
        int n = plans.size();
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (plans.get(j).getPrice() > plans.get(maxIdx).getPrice()) {
                    maxIdx = j;
                }
            }
            Collections.swap(plans, i, maxIdx);
        }
    }

    // Merge Sort by company name
    public void mergeSortByCompanyName(List<dataSortingModel> plans, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortByCompanyName(plans, left, mid);
            mergeSortByCompanyName(plans, mid + 1, right);
            merge(plans, left, mid, right);
        }
    }

    private void merge(List<dataSortingModel> plans, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        List<dataSortingModel> leftPlans = new ArrayList<>();
        List<dataSortingModel> rightPlans = new ArrayList<>();

        for (int i = 0; i < n1; i++) {
            leftPlans.add(plans.get(left + i));
        }
        for (int i = 0; i < n2; i++) {
            rightPlans.add(plans.get(mid + 1 + i));
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftPlans.get(i).getCompanyName().compareTo(rightPlans.get(j).getCompanyName()) <= 0) {
                plans.set(k, leftPlans.get(i));
                i++;
            } else {
                plans.set(k, rightPlans.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            plans.set(k, leftPlans.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            plans.set(k, rightPlans.get(j));
            j++;
            k++;
        }
    }

    // Quick Sort by billing cycle (monthly/yearly)
    public void quickSortByBillingCycle(List<dataSortingModel> plans, int low, int high) {
        if (low < high) {
            int pi = partition(plans, low, high);
            quickSortByBillingCycle(plans, low, pi - 1);
            quickSortByBillingCycle(plans, pi + 1, high);
        }
    }

    private int partition(List<dataSortingModel> plans, int low, int high) {
        String pivot = plans.get(high).getBillingCycle();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (plans.get(j).getBillingCycle().compareTo(pivot) <= 0) {
                i++;
                Collections.swap(plans, i, j);
            }
        }
        Collections.swap(plans, i + 1, high);
        return i + 1;
    }

    // Method to load CSV data into the plans list
    public List<dataSortingModel> loadCSVData() {
        List<dataSortingModel> plans = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header line
                    continue;
                }

                String[] values = line.split(",");
                String companyName = values[0];
                String planName = values[1];
                String priceWithDollar = values[2].trim(); // Keep the $ sign as a String
                String billingCycle = values[3];
                int maxUsers = Integer.parseInt(values[4]);
                String features = values[5].replace(";", ","); // Replace semicolon with a comma if needed

                // Remove the $ sign and parse the numerical value for sorting
                double price = Double.parseDouble(priceWithDollar.replace("$", "").trim());

                // Add data to the list with both display price (with $) and parsed price
                plans.add(new dataSortingModel(companyName, planName, priceWithDollar, price, billingCycle, maxUsers, features));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return plans;
    }
}
