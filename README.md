# TheDelivery
 The app is a delivery management system designed to streamline the process of creating, tracking, and delivering packages. It caters to two distinct user roles: users and couriers.

Login:
The app presents a secure login page, offering users and couriers the ability to authenticate themselves by selecting their respective roles.

User Interface:
Upon successful login, users are presented with the user interface. The interface encompasses essential features such as a search view for tracking packages by their tracking numbers, a display of previous deliveries, and package creation process triggered by clicking the designated "Add" button.

Courier Functionality:
Once logged in, they gain access to a curated list of available packages awaiting pickup. Couriers are empowered to select a package from the list, signifying its pickup by changing its status to "Picked up." Consequently, the corresponding "Pick Up" button associated with the package is discreetly hidden or rendered non-functional, ensuring clarity and preventing inadvertent duplicate pickups.

Package Delivery:
Following the pickup process, couriers can deliver the packages entrusted to them by selecting the "Deliver" button associated with the respective package. This action promptly updates the package status to "Delivered." Additionally, the card representing the package may either be seamlessly removed from the interface or visually marked as delivered, distinguishing it from ongoing deliveries.

Dynamic Package Card Generation:
The app dynamically generates package cards based on the packages stored in the Firebase Realtime Database. Each card exhibits the package details.

Firebase Integration:
the app integrates with the Firebase Realtime Database. 
