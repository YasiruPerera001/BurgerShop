import java.util.*;
//Burger Template...
class Burger{
	public static final int burgerPrice = 500;
	public static final int PREPARING=0; 
	public static final int DELIVERED=1; 
	public static final int CANCEL=2;
	private String orderID;
	private String customerID;
	private String name;
	private int quantity;
	private double total;
	private int status;
	Burger(){
		
	}
	Burger(String orderID,String customerID,String name,int quantity,double total,int status){
		this.orderID = orderID;
		this.customerID = customerID;
		this.name = name;
		this.quantity = quantity;
		this.total = total;
		this.status = status;
	}
	
	public void setOrderID(String orderID){
		this.orderID = orderID;
	}
	public String getOrderID(){
		return orderID;
	}
	public void setCustomerID(String customerID){
		this.customerID = customerID;
	}
	public String getCustomerID(){
		return customerID;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
	public int getQuantity(){
		return quantity;
	}
	public void setTotal(double total){
		this.total = total;
	}
	public double getTotal(){
		return total;
	}
	public void setStatus(int status){
		this.status = status;
	}
	public int getStatus(){
		return status;
	}
}
//Main class
class BurgerShop{
	public static Burger[] burgers = new Burger[0];
	public static int count = 0;
	//Generate and return OrderID
	public static String printOrderID(){
		int[] id = new int[4];
		//generating process
		for(int i=0;i<=count;i++){
			int c = 1;
			int temp = i;
			while(temp!=0){
				id[id.length-c]=temp%10;
				c++;
				temp/=10;
			}	
		}
		//Final order ID
		String orderid = "B";
		for(int a : id){
			orderid+=a;
		}
		//burgers[count-1].setOrderID(orderid);
		return orderid;
	}
	//Total bill value
	public static double findTotal(int qt){
		double total = Burger.burgerPrice * qt;
		return total;
	}
	//Extend array method
	public static void extendArray(){
		Burger[] temp = new Burger[burgers.length+1];
		for(int i=0;i<burgers.length;i++){
			temp[i]=burgers[i];
		}
		burgers=temp;
	}
	//Requirements for place order
	public static void requirementsAndTotalBill(String orderID){
		Scanner input = new Scanner(System.in);
		//System.out.println(count);
		boolean alreadyCus = false;
		int index = 0;
		String no = "";
		String name = "";
		int qt = 0;
		L1:while(true){
			System.out.print("Enter Customer ID (phone no.): ");
			no = input.next();
			for(int i=0;i<no.length();i++){
				char ch = no.charAt(i);
				if(!(ch>='0'&&ch<='9')){
					System.out.println("Sorry!! Invalid Customer ID, Try again...\n");
					continue L1;
				}
			}
			for(int i=0;i<no.length();i++){
				char ch = no.charAt(i);
				if(i==0&&ch=='0'&&no.length()==10){
					break L1;
				}
				else{
					System.out.println("Sorry!! Invalid Customer ID, Try again...\n");
					continue L1;
				}
			}
		}
		
		for(int i=0;i<burgers.length;i++){
			if(burgers[i]==null){
				continue;
			}
			if(no.equals(burgers[i].getCustomerID())){
				alreadyCus=true;
				index=i;
				break;
			}
		}
		if(!alreadyCus){
			System.out.print("Customer Name		: ");
			name = input.next();
		}
		else{
			System.out.println("Customer Name : "+burgers[index].getName());
			name = burgers[index].getName();
			burgers[index].setName(name);
		}
		L2:while(true){
			System.out.print("Enter Burger Quantity	- ");
			qt = input.nextInt();
			
			if(qt>0){
				break L2;
			}
			else{
				System.out.println("\nSorry!! Invalid quantity, Try again...\n");
				continue L2;
			}
		}
		int st = Burger.PREPARING;
		double total = findTotal(qt);
		System.out.printf("Total value - %.2f\n",total);
		
	//Confirming Order...
		System.out.print("\n\tAre you confirming the order?(Y/N) ");
		String op = input.next();
		
		//System.out.println(count);
		
		if(op.equalsIgnoreCase("Y")){
			count++;
			extendArray();
			for(int i=count-1;i<burgers.length;i++){
				if(burgers[i]==null){
					burgers[i] = new Burger();
					burgers[i].setOrderID(orderID);
					burgers[i].setCustomerID(no);
					burgers[i].setName(name);
					burgers[i].setQuantity(qt);
					burgers[i].setTotal(total);
					burgers[i].setStatus(st);
					break;
				}
			}
			System.out.println("\n\tOrder details are added to the system successfully...");
		/*	for(int i=0;i<burgers.length;i++){
				if(burgers[i]!=null){
					System.out.println(burgers[i].getOrderID()+" "+burgers[i].getCustomerID()+" "+burgers[i].getName());
				}
			}
			for(int i=0;i<burgers.length;i++){
				System.out.print(burgers[i]+" ");
			}*/
		}
		else if(op.equalsIgnoreCase("N")){
			System.out.println("\n\tOrder details are not added to the system...");
			
		}
	}
	//option 01 (place order)
	public static void placeOrder(){
		String opt;
		do{
			clearConsole();
			placeOrderInterface();
			String orderid = printOrderID();
			System.out.println("\nORDER ID - "+orderid);
			System.out.println("================\n\n");
			requirementsAndTotalBill(orderid);
			
			opt = promptOne("place another order");
			if(opt.equalsIgnoreCase("y")||opt.equalsIgnoreCase("yes")){
				continue;
			}
			else if(opt.equalsIgnoreCase("n")||opt.equalsIgnoreCase("no")){
				break;
			}
		}while(true);
	}
	public static void copyArray(Burger[] temp){
		for(int i=0;i<temp.length;i++){
			if(burgers[i] != null){
				temp[i] = new Burger(
					burgers[i].getOrderID(),
					burgers[i].getCustomerID(),
					burgers[i].getName(),
					burgers[i].getQuantity(),
					burgers[i].getTotal(),
					burgers[i].getStatus()
				);
			}
		}
	}
	public static void sort(Burger[] temp){
		for(int i=temp.length-1;i>0;i--){
			for(int j=0;j<i;j++){
				if(temp[j].getTotal()<temp[j+1].getTotal()){
					Burger t=temp[j+1];
					temp[j+1]=temp[j];
					temp[j]=t;
				}
			}
		}
	}
	public static void removeDuplicates(Burger[] temp,String str1,String str2){
		double tot1 = 0;
		double tot2 = 0;
		for(int i=0;i<temp.length;i++){
			for(int j=i+1;j<temp.length;j++){
				if(temp[i]!=null&&temp[j]!=null){
					if(temp[i].getName().equals(temp[j].getName())){
						tot1 = temp[i].getTotal();
						tot2 = temp[j].getTotal();
						tot1+=tot2;
						temp[i].setTotal(tot1);
						temp[j].setTotal(0);
					}
				}
			}
		}
		for(int i=0;i<temp.length;i++){
			for(int j=i+1;j<temp.length;j++){
				if(temp[i]!=null&&temp[j]!=null){
					if(temp[i].getName().equals(temp[j].getName())){
						temp[j]=null;
					}
				}
			}
		}
		int k=0;
		for(int i=0;i<temp.length;i++){
			if(temp[i]!=null){
				temp[k]=temp[i];
				k++;
			}
		}
		Burger[] temp2 = new Burger[k];
		//System.out.println(k);
		for(int i=0;i<temp2.length;i++){
			temp2[i]=temp[i];
		}
	/*	for(Burger a : temp2){
			if(a!=null){
				System.out.print(a.getName()+" "+a.getTotal()+"\n");
			}
		}*/
		temp=temp2;	
	/*	for(Burger a : temp){
			if(a!=null){
				System.out.print(a.getName()+" "+a.getTotal()+"\n");
			}
		}*/
		sort(temp);
		for(int i=0;i<temp.length;i++){
			if(temp[i]!=null){
				System.out.printf("%5s%12s%20.2f%6s%n%s%n",temp[i].getCustomerID(),temp[i].getName(),temp[i].getTotal(),str2,str1);
			}
		}
	}
	public static void bestCustomerGrid(){
		String str1 = "------------------------------------------------";
		String str2 = "Customer ID";
		String str3 = "Name";
		String str4 = "Total";
		String str5 = "|";
		
		System.out.printf("\n\n%s\n%5s%10s%20s%7s\n%s\n",str1,str2,str3,str4,str5,str1);
		
		Burger[] temp = new Burger[burgers.length];
		
		copyArray(temp);
	/*	for(int i=0;i<temp.length;i++){
			if(burgers[i]!=null){
				System.out.println(temp[i].getOrderID()+" "+temp[i].getCustomerID()+" "+temp[i].getName());
			}
		}*/
		removeDuplicates(temp,str1,str5);
	}
	//option 02 (Search best Customer)
	public static void searchBestCustomer(){
		String opt;
		do{
			clearConsole();
			bestCustomerInterface();
			bestCustomerGrid();
			
			opt = promptOne("go to Mainmenu");
			if(opt.equalsIgnoreCase("y")||opt.equalsIgnoreCase("yes")){
				break;
			}
			else if(opt.equalsIgnoreCase("n")||opt.equalsIgnoreCase("no")){
				continue;
			}
		}while(true);
	}
	public static void orderDetails(String id){
		Scanner input = new Scanner(System.in);
		String st = "";
		if(checkOrderID(id)!=-1){
			String str1 = "--------------------------------------------------------------------------------------------";
			String str2 = "Order ID";
			String str3 = "Customer ID";
			String str4 = "Name";
			String str5 = "Quantity";
			String str6 = "OrderValue";
			String str7 = "OrderStatus";
			String str8 = "|";
			
			System.out.printf("\n%s\n%3s%20s%10s%15s%17s%18s%5s\n%s\n",str1,str2,str3,str4,str5,str6,str7,str8,str1);
			
			for(int i=0;i<burgers.length;i++){
				if(id.equalsIgnoreCase(burgers[i].getOrderID())){
					switch(burgers[i].getStatus()){
						case 0:
							st="Preparing";
							break;
						case 1:
							st="Dilivered";
							break;
						case 2:
							st="Canceled";
							break;
					}
					System.out.printf("%3s%22s%12s%10d%20.2f%19s%5s\n%s\n",id,burgers[i].getCustomerID(),burgers[i].getName(),burgers[i].getQuantity(),burgers[i].getTotal(),st,str8,str1);
					break;
				}
			}
		}
		else{
			System.out.print("\nInvalid Order ID.");
		}
	}
	//option 03 (Search order)
	public static void searchOrder(){
		String opt;
		do{
			clearConsole();
			searchOrderInterface();
			String id = promptTwo();
			orderDetails(id);
			opt = promptOne("search another Order?");
			if(opt.equalsIgnoreCase("y")||opt.equalsIgnoreCase("yes")){
				continue;
			}
			else if(opt.equalsIgnoreCase("n")||opt.equalsIgnoreCase("no")){
				break;
			}
		}while(true);
	}
	public static void customerDetails(String cusID){
		int index = checkCustomerID(cusID);
		for(int i=0;i<burgers.length;i++){
			if(checkCustomerID(cusID)!=-1){
				System.out.println("\nCustomer ID 	- "+cusID);
				System.out.println("Name		- "+burgers[index].getName());
				break;
			}
		}
	}
	public static void customerOrderDetails(String cusID){
		if(checkCustomerID(cusID)!=-1){
			System.out.println("\nCustomer Order Details");
			System.out.println("=======================\n");
			
			String str1 = "---------------------------------------------------------";
			String str2 = "Order ID";
			String str3 = "Order Quantity";
			String str4 = "Total_Value";
			
			System.out.printf("\n%s\n%3s%20s%20s\n%s\n",str1,str2,str3,str4,str1);
			
			for(int i=0;i<burgers.length;i++){
				if(cusID.equals(burgers[i].getCustomerID())){
					System.out.printf("%3s%20d%20.2f\n",burgers[i].getOrderID(),burgers[i].getQuantity(),burgers[i].getTotal());
				}
			}
			System.out.println(str1);
		}
		else{
			System.out.println("\nThis customer ID is not added yet....\n");
		}
	}
	//option 04 (Search Customer)
	public static void searchCustomer(){
		String opt;
		do{
			clearConsole();
			searchCustomerInterface();
			String cusID = promptThree();
			customerDetails(cusID);
			customerOrderDetails(cusID);
			opt = promptOne("search another Customer?");
			if(opt.equalsIgnoreCase("y")||opt.equalsIgnoreCase("yes")){
				continue;
			}
			else if(opt.equalsIgnoreCase("n")||opt.equalsIgnoreCase("no")){
				break;
			}
		}while(true);
	}
	public static void orderGrid(int op){
		Scanner input = new Scanner(System.in);
		String str1 = "-------------------------------------------------------------------------";
		String str8 = "|";
		if(checkOption(op)&&op==2){
			clearConsole();
			bannerPrint("PREPARING ORDER");
			columnPrint(str1,str8);
			//System.out.println(Arrays.toString(status));
			for(int i=0;i<burgers.length;i++){
				if(burgers[i].getStatus()==0){
					System.out.printf("%3s%20s%12s%10d%20.2f%6s\n%s\n",burgers[i].getOrderID(),burgers[i].getCustomerID(),burgers[i].getName(),burgers[i].getQuantity(),burgers[i].getTotal(),str8,str1);
				}
			}
		}
		else if(checkOption(op)&&op==1){
			clearConsole();
			bannerPrint("DELIVERED ORDER");
			columnPrint(str1,str8);
			//System.out.println(Arrays.toString(status));
			for(int i=0;i<burgers.length;i++){
				if(burgers[i].getStatus()==1){
					System.out.printf("%3s%20s%12s%10d%20.2f%6s\n%s\n",burgers[i].getOrderID(),burgers[i].getCustomerID(),burgers[i].getName(),burgers[i].getQuantity(),burgers[i].getTotal(),str8,str1);
				}
			}
		}
		else if(checkOption(op)&&op==3){
			clearConsole();
			bannerPrint("CANCELED ORDER");
			columnPrint(str1,str8);
			//System.out.println(Arrays.toString(status));
			for(int i=0;i<burgers.length;i++){
				if(burgers[i].getStatus()==2){
					System.out.printf("%3s%20s%12s%10d%20.2f%6s\n%s\n",burgers[i].getOrderID(),burgers[i].getCustomerID(),burgers[i].getName(),burgers[i].getQuantity(),burgers[i].getTotal(),str8,str1);
				}
			}
		}
		else{
			System.out.println("Invalid option...");
		}
	}
	//option 05 (View orders)
	public static void viewOrderDetails(){
		String opt;
		do{
			clearConsole();
			viewOrderdetailsInterface();
			int op = promptFour();
			orderGrid(op);
			opt = promptOne("view another orders?");
			if(opt.equalsIgnoreCase("y")||opt.equalsIgnoreCase("yes")){
				continue;
			}
			else if(opt.equalsIgnoreCase("n")||opt.equalsIgnoreCase("no")){
				break;
			}
		}while(true);
	}
	public static void updateList(String orID){
		Scanner input = new Scanner(System.in);
		String st = "";
		String oid = "";
		String cid = "";
		String cname = "";
		System.out.println("\n");
		if(checkOrderID(orID)!=-1&&burgers[checkOrderID(orID)].getStatus()==0){
			for(int i=0;i<burgers.length;i++){
				if(orID.equalsIgnoreCase(burgers[i].getOrderID())){
					switch(burgers[i].getStatus()){
						case 0:
							st="Preparing";
							break;
					}
					oid=orID;
					cid=burgers[i].getCustomerID();
					cname=burgers[i].getName();
					System.out.printf("OrderID		- %s\nCustomer ID	- %s\nName		- %s\nQuantity	- %d\nOrderValue	- %.2f\nOrderStatus	- %s\n\n",orID,burgers[i].getCustomerID(),burgers[i].getName(),burgers[i].getQuantity(),burgers[i].getTotal(),st);
					break;
				}
			}
			updateOption();
			System.out.print("Enter your option - ");
			int op = input.nextInt();
			switch(op){
				case 1:
					quantityUpdate(oid,cid,cname);
					break;
				case 2:
					statusUpdate(oid,cid,cname);
					break;
			}
		}
		else if(checkOrderID(orID)!=-1&&burgers[checkOrderID(orID)].getStatus()==1){
			System.out.println("This order is already delivered...You can not update this order...");
		}
		else if(checkOrderID(orID)!=-1&&burgers[checkOrderID(orID)].getStatus()==2){
			System.out.println("This order is already canceled...You can not update this order...");
		}
		else{
			System.out.print("\nInvalid Order ID.");
		}
	}
	//check validation of status update options
	public static int checkStatus(){
		Scanner input = new Scanner(System.in);
		int op = 0;
		do{
			System.out.print("\nEnter new order status - ");
			op = input.nextInt();
			if(op>=0&&op<3){
				System.out.println("\t\nupdate Order status success fully....\n");
				break;
			}
			else{
				System.out.println("\t\nInvalid option, Please try again...\n");
				continue;
			}
		}while(true);
		return op;
	}
	//check validation of quantity values
	public static int checkVal(){
		Scanner input = new Scanner(System.in);
		int val = 0;
		do{
			System.out.print("\nEnter your quantity update value - ");
			val = input.nextInt();
			if(val>=0){
				System.out.println("\t\nupdate order quantity success fully....\n");
				break;
			}
			else{
				System.out.println("\t\nInvalid value, Please try again...\n");
				continue;
			}
		}while(true);
		return val;
	}
	//option 06 quantity update option
	public static void quantityUpdate(String oid,String cid,String cname){
		Scanner input = new Scanner(System.in);
		clearConsole();
		System.out.println("Quantity Update");
		System.out.println("================\n\n");
		System.out.println("Order ID	- "+oid);
		System.out.println("Customer ID	- "+cid);
		System.out.println("Name		- "+cname);
		
		int val = checkVal();
		double newTotal = (double)Burger.burgerPrice*val;
		for(int i=0;i<burgers.length;i++){
			if(oid.equalsIgnoreCase(burgers[i].getOrderID())){
				burgers[i].setQuantity(val);
				burgers[i].setTotal(newTotal);
				break;
			}
			
		}
		System.out.println("new order quantity - "+val);
		System.out.printf("new order value - %.2f",newTotal);
		//System.out.println(Arrays.toString(quantity));
		//System.out.println(Arrays.toString(totalVals));
	}
	//option 06 status update option
	public static void statusUpdate(String oid,String cid,String cname){
		Scanner input = new Scanner(System.in);
		clearConsole();
		System.out.println("Status Update");
		System.out.println("===============\n\n");
		System.out.println("Order ID	- "+oid);
		System.out.println("Customer ID	- "+cid);
		System.out.println("Name		- "+cname);
		System.out.println("\n\t(0)Cancel");
		System.out.println("\t(1)Preparing");
		System.out.println("\t(2)Delivered");
		
		int op = checkStatus();
		String st = "";
		switch(op){
					case 0:
						st="Canceled";
						break;
					case 1:
						st="Preparing";
						break;
					case 2:
						st="Delivered";
						break;
				}
		for(int i=0;i<burgers.length;i++){
			if(oid.equalsIgnoreCase(burgers[i].getOrderID())){
				switch(op){
					case 0:
						burgers[i].setStatus(2);
						break;
					case 1:
						burgers[i].setStatus(0);
						break;
					case 2:
						burgers[i].setStatus(1);
						break;
				}
			}
		}
		System.out.println("new order status - "+st);
		//System.out.println(Arrays.toString(status));
		//System.out.println(Arrays.toString(totalVals));
	}
	//option 06 (Update order details)
	public static void updateOrderDetails(){
		String opt;
		do{
			clearConsole();
			updateOrderDetailsInterface();
			String orID = promptTwo();
			updateList(orID);
			opt = promptOne("update another order details?");
			if(opt.equalsIgnoreCase("y")||opt.equalsIgnoreCase("yes")){
				continue;
			}
			else if(opt.equalsIgnoreCase("n")||opt.equalsIgnoreCase("no")){
				break;
			}
		}while(true);
	}
	public static void main(String args[]){
		//statusArrayReset();
		systemRun();
	}
	//Main part System running....
	public static void systemRun(){
		int op;
		int count = 1;
		String opt;
		do{
			clearConsole();
			op = mainMenu();
			switch(op){
				case 1:
					placeOrder();
					break;
				case 2:
					searchBestCustomer();
					break;
				case 3:
					searchOrder();
					break;
				case 4:
					searchCustomer();
					break;
				case 5:
					viewOrderDetails();
					break;
				case 6:
					updateOrderDetails();
					break;
				case 7:
					exit();
					break;
				default:
					System.out.println("Invalid option Please enter a valid option...");
					continue;
			}
		}while(op!=7);
	}
	//Mainmenu
	public static int mainMenu(){
		//welcome banner
		String str1 = "------------------------------------------------------------------------";
		String str2 = "iHungry Burger";
		String str3 = "|";
		
		System.out.printf("%s%n%s%42s%29s%n%s%n%n",str1,str3,str2,str3,str1);
		
		//option list
		String op1 = "[1] Place order";
		String op2 = "[2] Search Best Customer";
		String op3 = "[3] Search Order";
		String op4 = "[4] Search Customer";
		String op5 = "[5] View Orders";
		String op6 = "[6] Update Order Details";
		String op7 = "[7] Exit";
		System.out.printf("%s%40s%n%s%34s%n%s%40s%n%s",op1,op2,op3,op4,op5,op6,op7);
		
		//User input for option
		Scanner input = new Scanner(System.in);
		System.out.print("\n\nEnter an option to continure > ");
		int op = input.nextInt();
		
		return op;
	}
	public static void updateOption(){
		System.out.println("What do you want to update ?");
		System.out.println("\t(01) Quantity\n\t(02) Status\n");
	}
	public static boolean checkOption(int op){
		if(op==1||op==2||op==3){
			return true;
		}
		return false;
	}
	public static int checkOrderID(String id){
		for(int i=0;i<burgers.length;i++){
			if(burgers[i].getOrderID().equalsIgnoreCase(id)){
				return i;
			}
		}
		return -1;
	}
	public static int checkCustomerID(String cusID){
		for(int i=0;i<burgers.length;i++){
			if(cusID.equals(burgers[i].getCustomerID())){
				return i;
			}
		}
		return -1;
	}
	public static String promptOne(String statement){
		Scanner input = new Scanner(System.in);
		System.out.print("\nDo you want to "+statement+"(Y/N)> ");
		String op = input.next();
		return op;
	}
	public static String promptTwo(){
		Scanner input = new Scanner(System.in);
		System.out.print("\nEnter Order id - ");
		String op = input.next();
		return op;
	}
	public static String promptThree(){
		Scanner input = new Scanner(System.in);
		System.out.print("Enter customer id - ");
		String id = input.next();
		
		return id;
	}
	public static int promptFour(){
		Scanner input = new Scanner(System.in);
		System.out.print("Enter an option to continue > ");
		int op = input.nextInt();
		
		return op;
	}
	//printing processes
	public static void columnPrint(String str1,String str8){
		String str2 = "Order ID";
		String str3 = "Customer ID";
		String str4 = "Name";
		String str5 = "Quantity";
		String str6 = "OrderValue";
		
		System.out.printf("\n%s\n%3s%20s%10s%15s%15s%5s\n%s\n",str1,str2,str3,str4,str5,str6,str8,str1);
	}
	public static void bannerPrint(String statement){
		String str1 = "------------------------------------------------------------------------";
		String str2 = statement;
		String str3 = "|";
		
		System.out.printf("%s%n%s%42s%29s%n%s%n%n",str1,str3,str2,str3,str1);
	}
	//Interfaces of main options
	public static void updateOrderDetailsInterface(){
		//welcome banner
		String str1 = "------------------------------------------------------------------------";
		String str2 = "UPDATE ORDER DETAILS";
		String str3 = "|";
		
		System.out.printf("%s%n%s%42s%29s%n%s%n%n",str1,str3,str2,str3,str1);
	}
	public static void viewOrderdetailsInterface(){
		//welcome banner
		String str1 = "------------------------------------------------------------------------";
		String str2 = "VIEW ORDER LIST";
		String str3 = "|";
		
		System.out.printf("%s%n%s%42s%29s%n%s%n%n",str1,str3,str2,str3,str1);
		
		System.out.println("[1] Delivered Order");
		System.out.println("[2] Preparing Order");
		System.out.println("[3] Canceled Order\n");
	}
	public static void searchCustomerInterface(){
		//welcome banner
		String str1 = "------------------------------------------------------------------------";
		String str2 = "SEARCH CUSTOMER DETAILS";
		String str3 = "|";
		
		System.out.printf("%s%n%s%42s%29s%n%s%n%n",str1,str3,str2,str3,str1);
	}
	public static void searchOrderInterface(){
		//welcome banner
		String str1 = "------------------------------------------------------------------------";
		String str2 = "SEARCH ORDER DETAILS";
		String str3 = "|";
		
		System.out.printf("%s%n%s%42s%29s%n%s%n%n",str1,str3,str2,str3,str1);
	}
	public static void bestCustomerInterface(){
		//welcome banner
		String str1 = "------------------------------------------------------------------------";
		String str2 = "BEST Customer";
		String str3 = "|";
		
		System.out.printf("%s%n%s%42s%29s%n%s%n%n",str1,str3,str2,str3,str1);
	}
	public static void placeOrderInterface(){
		//welcome banner
		String str1 = "------------------------------------------------------------------------";
		String str2 = "PLACE ORDER";
		String str3 = "|";
		
		System.out.printf("%s%n%s%42s%29s%n%s%n%n",str1,str3,str2,str3,str1);
	}
	//clear console method
	public final static void clearConsole(){
		try{
			final String os = System.getProperty("os.name"); 
			if(os.contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			}else{
				System.out.print("\033[H\033[2J");
				System.out.flush();
			}
		}catch(final Exception e){
			e.printStackTrace();
			// Handle any exceptions.
		}
	}
	//exit method
	public static void exit(){
		clearConsole();
		System.out.println("\n\t\tYou left the program...\n");
		System.exit(0);
	}
}
