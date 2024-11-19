# FLASH

FLASH is an android delivery management system designed to streamline the process of creating, tracking, and delivering packages. It serves two user roles: **Users** and **Couriers**, each with tailored functionalities to optimize the delivery experience.

## Features

### User Functionality
- **Login**: Secure login page that allows users and couriers to authenticate by selecting their respective roles.
- **Package Tracking**: After login, users can easily search for and track packages by their tracking numbers.
- **Previous Deliveries**: Users can view a history of their previous deliveries.
- **Package Creation**: Users can initiate new deliveries by clicking the "Add" button to create and submit packages for delivery.

### Courier Functionality
- **Available Packages**: Couriers can view a curated list of packages waiting for pickup.
- **Pickup Confirmation**: Couriers can select a package and mark it as "Picked up," which updates its status and hides or disables the corresponding "Pick Up" button to prevent accidental double pickups.
- **Delivery Completion**: Once a package is delivered, couriers can click the "Deliver" button, changing the package status to "Delivered." The package card is either removed or visually marked as delivered, clearly distinguishing completed deliveries from those in progress.

### Dynamic Package Cards
- **Real-Time Updates**: The app dynamically generates package cards based on data stored in the Firebase Realtime Database, ensuring that the interface always reflects the most up-to-date information.
- **Package Details**: Each package card displays relevant information, including tracking numbers, status, and other necessary details for both users and couriers.

### Firebase Integration
- The app integrates with **Firebase Realtime Database** to securely store and sync package data, providing real-time updates for both users and couriers.

## Technology Stack
- **Programming Language**: Java
- **Development Environment**: Android Studio
- **Database**: Firebase Realtime Database
- **Authentication**: Firebase Authentication
- **User Interface**: Android Views and Components

## Installation
1. Clone this repository to your local machine.
2. Open the project in **Android Studio**.
3. Connect the project to Firebase by following the official Firebase setup instructions for Android.
4. Run the app on an Android device or emulator.

App photes : https://drive.google.com/drive/folders/1EYrk6MbevIn-97l0p3wHTnZtgPd67gPk?usp=drive_link
