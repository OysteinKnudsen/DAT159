package videostore;
import videostore.Rental;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {
    private String name;
    private Vector<Rental> _rentals = new Vector<Rental>();

    public Customer(String name) {
        this.name = name;
    }

    public String statement() {
        double totalAmountOwed = 0;
        int frequentRenterPoints = 0;
        Enumeration<Rental> rentals = _rentals.elements();
        String result = "Rental Record for " + getName() + "\n";
                
        while (rentals.hasMoreElements()) {
        	
            Rental rentalElement = rentals.nextElement();
            double amountOwed = calculateAmountOwed(rentalElement);
            System.out.println("Hello");
            frequentRenterPoints += calculateFrequentRenterPoints(rentalElement);         
            result += generateFigures(rentalElement, amountOwed);            
            totalAmountOwed += amountOwed;
        }
        result += generateFooterLines(totalAmountOwed, frequentRenterPoints);
        
        return result;
    }
    
    private String generateFigures(Rental rentalElement, double amountOwed) {    	
    	return "\t" + rentalElement.getMovie().getTitle()+ "\t" +
        String.valueOf(amountOwed) + "\n";   	
    }
    
    private int calculateFrequentRenterPoints(Rental rentalElement) {
    	boolean isNewRelease = rentalElement.getMovie().getPriceCode() == Movie.NEW_RELEASE;
    	boolean rentedLongerThanOneDay = rentalElement.getDaysRented() > 1;
    	
    	if (isNewRelease && rentedLongerThanOneDay) {
    		return 2;
    	} else return 1;
    }
    
    private double calculateAmountOwed(Rental rentalElement) {
    	double amountOwed = 0.0;
    	int daysRented = rentalElement.getDaysRented();
    	
    	switch (rentalElement.getMovie().getPriceCode()) {
        case Movie.REGULAR:
            amountOwed += 2;
            if (daysRented > 2)
                amountOwed += (daysRented - 2) * 1.5;
            break;
        case Movie.NEW_RELEASE:
            amountOwed += daysRented * 3;
            break;
        case Movie.CHILDRENS:
            amountOwed += 1.5;
            if (daysRented > 3)
                amountOwed += (daysRented - 3) * 1.5;
            break;
    	}
    	return amountOwed;
    }
         
    private String generateFooterLines(double totalAmount, int frequentRenterPoints) {
    	String footerLines = "";
    	footerLines += "Amount owed is " + String.valueOf(totalAmount) + "\n";
    	footerLines += "You earned " + String.valueOf(frequentRenterPoints) +
                " frequent renter points";
    	return footerLines;
    }

    public void addRental(Rental arg) {
        _rentals.addElement(arg);
    }

    public String getName() {
        return name;
    }
}
