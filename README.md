First step

I want to be able to add products to a shopping cart.
In the end, the user should see the total price. 
The total price has to be calculated from item price, items count and discounts. 
Items are limited and are stored in storage.
And remember that price is more complex than simple double.

Storage
The storage is a list of the unit price, available items, and their quantities. 
The initial values are loaded from a file. Date in the file should be json.

Discounts
Your shopping cart should support the following offers:
1. Buy 3 Get One Free
2. Buy 1 Get 30% Off
3. Others

(In future it will be great if user can add discount for specific item)

Running the program
The application should allow input in two different ways:
1) if only the storage argument is provided, the application starts an prompt, where the actions can be typed in;
2) if the commands file path is provided as a second argument, such a file is parsed and the commands in it executed one by one.

In both cases, the commands are separated by a new line.
If two discount are applied at the same product, only the more recent one stands.
Interactive mode

$ ./market strorage.json

$ start

$ application v 1.2 started

$ add bear 5

5 bear(s) was added

$ add cola 1

1 cola(s) was added

$ price

discount:00.00,price:XX.50

$ discount buy_3_get_1_free soap

discount added

$ price

discount:X.00,price:XX.50

$ add soap 2

added soap 2

$ price

discount:XX.00,price:XX.50

$ discount buy_1_get_30_percentage

discount was added

$ add milk 1

1 milk(s) was added

$ price

discount:X.00, price:XX.50

$ finish

done

File mode
$ ./market storage.json commadsList.txt

In a team of 3 persons. I recommend split work to severtal parts

Read and write data to file. Work with storage file(Database). API for access to database.  
Work with a market basket. Add products or remove from baskets(Service And API) TeamLeader, class design, integration.
Output and input information and read commands from file (Web).
Discounts logic -

All logic has to be covered by Unit tests

Second step

Instead of file use database
Add web interface
Mark create or introduce API for web
Gradle, Maven

