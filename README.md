# Hyperlocal

A Shopping app that can help users to look for nearby shops for any particular items.
It lets the user compare the rates of the item and visit the nearest, cheapest and the best shop. 

/*
    TODO
    1. Login using Google.
        1.1 If the person has previously logged in, then just update the contents in database
        1.2 If new person, then create a new document
    2. Logout
        2.1 Delete the document of the corresponding email-id
    3. Upload data to firebase
        3.1 Add a section in the navigation drawer for the facility to create-
            3.1.1 categories - name, image
            3.1.2 sub-categories - name
            3.1.3 store - name, id, location
    4. Category List
        4.1 Fetch from firebase instead of manually entering the values
    5. Products list
        5.1 Display a list of sub-categories on clicking the category in a fragment
    6. Store list
        6.1 Check if location permission exists or not. If exists then
            6.1.2 Show the list of stores selling that product with the minimum distance from the useer
        6.2 If location permission does not exist then ask for it
            6.2.1 If accepted then do 6.1
            6.2.2 Else show the list of stores randomly.
    7.Search Query
        7.1 Should be able to search the categories or subcategories
        7.2 When a particular search matches, open another activity as the one which opens on clicking the product
    8. Navigation
        8.1 Open the Google maps by sending 2 locations to the intent.
            Location of the store and location of the person. else only location of the store.


 */