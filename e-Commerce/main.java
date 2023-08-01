import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Product{
	//VARIABLES
	private String name;
	private double basePrice;
	private double priceWithVat;
	private double price;
	private boolean typeOfProduct;
	private static double vatRateOrdinary = 0.1;
	private static double vatRateLuxury = 0.2;
	private double productPromotion;
	//CONSTRUCTORS
	public Product(String name, double basePrice, boolean typeOfProduct, double productPromotion) {
		this.name = name;
		this.typeOfProduct = typeOfProduct;
        this.productPromotion = productPromotion;
        this.setBasePrice(basePrice);
        if(typeOfProduct) {
        	this.priceWithVat = basePrice*(1 + vatRateLuxury);
    		this.price = priceWithVat *(1-productPromotion);
        }
        else {
        	this.priceWithVat = basePrice * (1 + vatRateOrdinary);
        	this.price = priceWithVat * (1-productPromotion);
        }
	}
	public Product(String name, double basePrice, boolean typeOfProduct) {
		this.name = name;
		this.typeOfProduct = typeOfProduct;
		this.setBasePrice(basePrice);
		if(typeOfProduct) {
    		this.price =  basePrice*(1 + vatRateLuxury);
        }
        else {
        	this.price = basePrice*(1 + vatRateOrdinary);
        }
	}	
    //GETTER SETTER METHODS
	public String getTypeOfProduct() {
		if (typeOfProduct) {			
		return "Luxury";
		}
		else {
		return "Ordinary";	
		}
	}
	public void setTypeOfProduct(boolean typeOfProduct) {
		this.typeOfProduct = typeOfProduct;
	}
	public double getPrice() {
		return price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static double getVatRateOrdinary() {
		return vatRateOrdinary;
	}
	public static void setVatRateOrdinary(double newVatRateOrdinary) {
		Product.vatRateOrdinary = newVatRateOrdinary;
	}
	public double getVatRateLuxury() {
		return vatRateLuxury;
	}
	public static void setVatRateLuxury(double newVatRateLuxury) {
		Product.vatRateLuxury = newVatRateLuxury;
	}
	public double getProductPromotion() {
		if (productPromotion != 0) return productPromotion;
		else return 0;
	}
	public void setProductPromotion(double productPromotion) {
		this.productPromotion = productPromotion;
	}
	public double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}
	public double getPriceWithVat() {
		return priceWithVat;
	}
	public String toString() {
		return " Product : " + getName() + "\n Price : " + getPrice()+ " TL" + "\n Type : " + getTypeOfProduct() + "\n Product Promotion Rate : " + getProductPromotion();
	}
	public void display() {
		System.out.println(this.toString());
	}
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
abstract class Customer{
	//VARIABLES
	private String name;
	private double totalPoint = 0;
	private double discountRate;
	private double pointRate;
	//CONSTRUCTOR
	public Customer(String name) {
		this.name = name;				
	}
	//GETTER SETTER METHODS
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
    public double getPoint() {
		return totalPoint;
	}
    public void addPoint(double point) {
    	this.totalPoint += point;
    }
    public void removePoint(double usedPoint) {
    	this.totalPoint -= usedPoint;
    }
	public double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	public double getPointRate() {
		return pointRate;
	}
	public void setPointRate(double pointRate) {
		this.pointRate = pointRate;
	}
	public String toString() {
		return " Customer Name : " + getName() + "\n Point : " + getPoint() + "\n Type : ";
	}
	public void display() {
		System.out.println(this.toString());
	}
	// ABSTRACT METHODS
	abstract double transactionPromotion(Transaction transaction);
	abstract void gainPoint(Transaction transaction);
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class Regular extends Customer{
	//VARIABLES
	private double limitDiscount = 40;
	private double limitPoint = 100;	
	//CONSTRUCTOR
	public Regular(String name) {
		super(name);
		setDiscountRate(0.03);
		setPointRate(0.02);		
	}
	//GETTER AND SETTER
	public double getLimitDiscount() {
		return limitDiscount;
	}
	public void setLimitDiscount(double limitDiscount) {
		this.limitDiscount = limitDiscount;
	}
	public double getLimitPoint() {
		return limitPoint;
	}
	public void setLimitPoint(double limitPoint) {
		this.limitPoint = limitPoint;
	}
	public String toString() {
		return super.toString() + "Regular";
	}	
	// ABSTRACT METHODS
	@Override
	void gainPoint(Transaction transaction) {
		if (transaction.getBasketPrice() >= limitPoint) {
			addPoint((transaction.getBasketPrice() - limitPoint ) * getPointRate());			
		}		
	}
	@Override
	double transactionPromotion(Transaction transaction) {
		if(transaction.getBasketPrice() >= limitDiscount) {
			return (transaction.getBasketPrice() - limitDiscount) * getDiscountRate();
		}
		else {
		return 0;	
		}				
	}	
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class Gold extends Customer{
	public Gold(String name) {
		super(name);
		setDiscountRate(0.05);
		setPointRate(0.03);		
	}
	public String toString() {
		return super.toString() + "Gold";
	}
	@Override
	double transactionPromotion(Transaction transaction) {
		return transaction.getBasketPrice() * getDiscountRate();		
	}
	@Override
	void gainPoint(Transaction transaction) {
		addPoint(transaction.getBasketPrice() * getPointRate());		
	}
}
class Transaction {
	private Customer customer;
	private HashMap<String, Double> productAmountPairs = new HashMap<String, Double>();
	private double basketPrice = 0;
	private double productPromotionPrice = 0;
	public Transaction(Customer customer) {
		this.customer = customer;		
	}
	public Transaction() {		
	}
	public double getBasketPrice() {
		return basketPrice;
	}
	public double getProductPromotionPrice() {
		return productPromotionPrice;
	}
	public void addProduct(Product product, double i) {
		if(!productAmountPairs.containsKey(product.getName())) {
		productAmountPairs.put(product.getName(), i);
		basketPrice += product.getPrice() * i;
		productPromotionPrice += product.getPriceWithVat() * product.getProductPromotion() * i;
		}
		else {
		productAmountPairs.replace(product.getName(), productAmountPairs.get(product.getName()) + i);
		basketPrice += product.getPrice() * i;
		productPromotionPrice += product.getPriceWithVat() * product.getProductPromotion() * i;		
		}				
	}
	public void cancelProduct(Product product, double i) {
		if(i < productAmountPairs.get(product.getName())) {
			productAmountPairs.replace(product.getName(), productAmountPairs.get(product.getName()) - i);
			basketPrice -= product.getPrice() * i;
			productPromotionPrice -= product.getPriceWithVat() * product.getProductPromotion() * i;		
			
		}
		else if(i == productAmountPairs.get(product.getName())) {
			productAmountPairs.remove(product.getName());
			basketPrice -= product.getPrice() * i;
			productPromotionPrice -= product.getPriceWithVat() * product.getProductPromotion() * i;			
		}
	}
	
	public double invoice() {
		if(customer != null) {
			this.basketPrice -= customer.transactionPromotion(this);
			customer.gainPoint(this);
			System.out.println("Customer name : "+ this.customer.getName() + "\n Product Promotion Price : " + getProductPromotionPrice()+ " TL" + "\n Transaction Promotion Price : " + customer.transactionPromotion(this) + " TL"+ "\n Total Point : " + customer.getPoint() + "\n Total Payment : " + getBasketPrice()+ " TL");
			Scanner scanner = new Scanner (System.in);
			System.out.println(" How much your points do you want to use ?");
			double usedPoint = scanner.nextDouble();
			
			System.out.println("************************************************************");
			if( usedPoint <= customer.getPoint()) {
				customer.removePoint(usedPoint);
			    System.out.println("Customer name : "+ this.customer.getName() + "\n Total Payment : " + (getBasketPrice() - usedPoint)+ " TL" + "\n Total Point : " + customer.getPoint());
			    
			}
			else {
				 System.out.println("Customer name : "+ this.customer.getName() + "\n Total Payment : " + getBasketPrice()+ " TL");
				
			    }
			System.out.println("************************************************************");
	
			return getBasketPrice() - usedPoint;						
		}
		else {
			System.out.println("Customer name : Unregistered Customer");
			System.out.println(" Product Promotion Price : " + getProductPromotionPrice() + "\n Total Payment : " + getBasketPrice()+ " TL");
			return getBasketPrice();
		}			
	}
	
	public String toString() {
		if(this.customer != null) {
			return "Total Payment : " + invoice() + "\n Total Discount Amount : " + (getProductPromotionPrice() + customer.transactionPromotion(this) );

		}
		else {
			return "Total Payment : " + invoice() + "\n Total Discount Amount : " + getProductPromotionPrice();

			
		}
		
	}
	
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class main {
	public static void displayList(ArrayList a) {
		System.out.println("************************************************************");
		for(int i = 0; i < a.size() ;i++) {
			System.out.println(a.get(i).toString());
			System.out.println("************************************************************");
			
			
		}
	}
	public static void main(String[] args) {
		ArrayList<Product> Products = new ArrayList<Product>();	
		Products.add(new Product("Apple", 14 , false, 0.30));
		Products.add(new Product("Blueberry", 25, true, 0.17));	
		Products.add(new Product("Banana", 37, true));
		Products.add(new Product("Watermelon",15,false));
		Products.add(new Product("Grape",55,true));
		Products.add(new Product("Peach", 40, false,0.33));
		ArrayList<Customer> Customers = new ArrayList<Customer>();
		Customers.add(new Gold("Burak"));
		Customers.add(new Gold("Yunus Emre"));
		Customers.add(new Regular("Kaan"));
		Customers.add(new Regular("U�ur"));
		ArrayList<Transaction> Transactions = new ArrayList<Transaction>();
		Transactions.add(new Transaction(Customers.get(0)));
		Transactions.add(new Transaction(Customers.get(1)));
		Transactions.add(new Transaction(Customers.get(2)));
		Transactions.add(new Transaction(Customers.get(3)));
		Transactions.add(new Transaction());
		Transactions.get(0).addProduct(Products.get(0), 2);
		Transactions.get(0).addProduct(Products.get(1), 3);
		Transactions.get(0).addProduct(Products.get(2), 2);
		Transactions.get(0).invoice();
		Transactions.get(1).addProduct(Products.get(0), 2);
		Transactions.get(1).addProduct(Products.get(1), 2);
		Transactions.get(1).addProduct(Products.get(2), 2);
		Transactions.get(1).addProduct(Products.get(3), 2);
		Transactions.get(1).addProduct(Products.get(0), 2);
		Transactions.get(1).invoice();
		Transactions.get(2).addProduct(Products.get(0), 2);
		Transactions.get(2).addProduct(Products.get(2), 2);
		Transactions.get(2).addProduct(Products.get(3), 2);
		Transactions.get(2).addProduct(Products.get(5), 2);
		Transactions.get(2).addProduct(Products.get(1), 2);
		Transactions.get(2).addProduct(Products.get(4), 2);
		Transactions.get(2).invoice();
		Transactions.get(3).addProduct(Products.get(4), 2);
		Transactions.get(3).cancelProduct(Products.get(4), 1);
		Transactions.get(3).invoice();
		Transactions.get(4).addProduct(Products.get(0), 2);
		Transactions.get(4).cancelProduct(Products.get(0), 2);
		Transactions.get(4).addProduct(Products.get(4), 2);
		Transactions.get(4).addProduct(Products.get(5), 4);
		Transactions.get(4).addProduct(Products.get(3), 2);
		Transactions.get(4).addProduct(Products.get(2), 2);
		Transactions.get(4).invoice();
		
		
		


	}

}
