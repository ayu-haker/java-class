# Java MVC Calculator (Swing)

## 📌 Experiment

This project demonstrates the **Model-View-Controller (MVC)** architecture using **Java Swing**. It is a simple calculator that performs basic arithmetic operations.

## 🎯 Objective

* Understand the MVC design pattern.
* Develop a Java Swing GUI application.
* Separate business logic, user interface, and event handling.

## 🛠 Technologies Used

* Java
* Java Swing
* MVC Architecture

## 📂 Project Structure

```
Java-MVC-Calculator/
│── CalculatorModel.java
│── CalculatorView.java
│── CalculatorController.java
│── Main.java
└── README.md
```

## 📖 Description

### Model (`CalculatorModel.java`)

Contains the business logic for:

* Addition
* Subtraction
* Multiplication
* Division

### View (`CalculatorView.java`)

Creates the graphical user interface using Java Swing components such as:

* Labels
* Text Fields
* Buttons
* JFrame

### Controller (`CalculatorController.java`)

Handles user actions, receives input from the View, calls the Model for calculations, and displays the result.

### Main (`Main.java`)

Creates objects of the Model, View, and Controller to start the application.

## ✨ Features

* Addition (+)
* Subtraction (-)
* Multiplication (*)
* Division (/)
* Simple and user-friendly interface
* Follows MVC architecture

## ▶️ How to Run

1. Clone the repository:

```bash
git clone https://github.com/your-username/Java-MVC-Calculator.git
```

2. Open the project in your preferred Java IDE (IntelliJ IDEA, Eclipse, or NetBeans).

3. Compile all Java files.

4. Run:

```bash
Main.java
```

## 📷 Output

The application displays a calculator window where users can:

* Enter two numbers.
* Select an arithmetic operation.
* View the calculated result.

## 📚 Learning Outcomes

* Implemented the MVC design pattern.
* Built a Java Swing desktop application.
* Handled button events using ActionListener.
* Organized Java code into Model, View, and Controller components.

## 👨‍💻 Author

Ayushman Bosu Roy

**Course:** B.Tech (Artificial Intelligence)

**Experiment:** Java Swing MVC Calculator
