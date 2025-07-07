# Fruit Service Engine – Distributed Systems Assignment

>*MIT8102: Advanced Distributed Systems*  
*Strathmore University*  

**Group Members:** 

| NAME        | REG NO         |
|-------------|----------------|
| `JOEL MUTEVU` | [122173][PlDb] |
| `BRIAN KAMAU` | [224607][PlGh] |
|-------------|----------------|



  
**Date:** [2025-07-01]

---

## Table of Contents

1. [Introduction](#introduction)  
2. [Problem Statement](#problem-statement)  
3. [Why We Chose This Framework](#why-we-chose-this-framework)  
4. [System Architecture](#system-architecture)  
5. [Implementation Overview](#implementation-overview)  
6. [Step-by-Step Instructions](#step-by-step-instructions)  
7. [Demo and Screenshots](#demo-and-screenshots)  
8. [Development Diary](#development-diary)  
9. [References](#references)  
10. [Submission](#submission)  

---

## Introduction

Distributed systems are at the heart of modern computing, enabling us to build scalable and robust applications. For this assignment, we (Joel and I) were tasked with developing a distributed mobile application, the Fruit Service Engine, as part of MIT8102: Advanced Distributed Systems at Strathmore University.

---

## Problem Statement

The main objective was to create a distributed system that manages a fruit-price table, allowing mobile clients to perform CRUD operations and generate receipts, all through a remote service engine.

---

## Why We Chose This Framework

Given the flexibility in the assignment, we decided to use Java RMI for the backend and Jetpack Compose with Kotlin for the Android client. This decision was based on our familiarity with these technologies and the clean separation they offer between backend logic and user interface. We also found that using Tomcat 9.0 for hosting servlets was more stable with our development tools compared to Tomcat 10.

---

## System Architecture

```
Android App (Kotlin, Jetpack Compose)
      |
   HTTP (Retrofit)
      |
Java Servlet (Tomcat 9.0)
      |
   Java RMI
      |
Fruit Service Engine (Java)
```

---

## Implementation Overview

### Backend (Java RMI + Servlets)

We implemented the following classes as required:

- `FruitComputeEngine.java`
- `FruitComputeTaskRegistry.java`
- `AddFruitPrice.java`, `UpdateFruitPrice.java`, `DeleteFruitPrice.java`, `CalFruitCost.java`, `CalculateCost.java`

**Sample Code Block:**

```java
/**
 * Task.java
 * Interface for all fruit service tasks.
 */
public interface Task extends Serializable {
    Object execute();
}
```

### Android Client (Kotlin + Jetpack Compose)

```kotlin
/*
 * FruitServiceApi.kt
 * Retrofit interface for backend communication.
 */
interface FruitServiceApi {
    @POST("/addFruit")
    suspend fun addFruit(@Body fruit: Fruit): Response<Result>
}
```

---

>## Step-by-Step Instructions to run the code

### Backend Setup

1. **Clone the repository:**  
   [GitHub Repository – Fruit Service Engine](https://github.com/EngineerClout/JAVARMISERVLETS.git)

   
   [GitLab Repository – Fruit Service Engine](https://gitlab.com/bkamausafaricom/rmi_servlets_assignment.git)

2. **Build and deploy backend:**  
   - Open in IntelliJ IDEA.
   - Build the project and deploy the WAR to Tomcat 9.0.
   - Start Tomcat and ensure the web application is running.

3. **Start RMI Registry:**  
   ```sh
   start rmiregistry 1099
   ```

4. **Run RMI Server:**  
   - Run the main class for the RMI server, which is `FruitServiceEngine.java`.

### Android Client Setup

1. **Clone the Android project** (or copy from `https://github.com/EngineerClout/JAVARMISERVLETS.git`).
2. **Open in Android Studio** and sync Gradle.
3. **Set backend IP** in the network module (ensure same LAN).
4. **Build and run the app** on your device or emulator.

2. **Configure NetworkModule.kt**
   - Hotspot your machine from the phone you’ll use for testing.
   - On your computer, open Command Prompt and run:
ipconfig
Find your IPv4 address (e.g., 192.168.43.172).
In Android Studio, open:
com.example.myapplication.networkservice.NetworkModule.kt
Update `line 13` to reflect your `IPV4 ADDRESS`:
```kotlin
private const val BASE_URL = "http://YOUR_IPV4_ADDRESS:8080/RMIASSIGNMENT_war_exploded/" 
```
           
6. **Build and Run**
   - Sync Gradle and build the project.
   - Run the app on the physical device providing the hotspot.
   - Ensure the device can reach the server at your `IPv4 address`.

---

# Alternative Deployment: Deploying the WAR File with Apache Tomcat 9 (Windows)

>(**For those who prefer not to build or download the full RMI backend code**)

If you prefer not to build or download the full RMI backend code, you can use the provided WAR file and deploy it directly to Tomcat 9 on any Windows machine. Follow these steps:

> **NB:** THE PATH TO THE WAR FILE IS `target/RMIASSIGNMENT-1.0-SNAPSHOT.war`

### 1. Download and Install Tomcat 9
- Visit: [Tomcat 9 Downloads](https://tomcat.apache.org/download-90.cgi)
- Download the **Windows Service Installer**.
- Run the installer and choose **Full installation**.
- Set the HTTP port to **8080** (default).
- Point to your JDK path when prompted.
- Complete the setup.
-  Open [http://localhost:8080](http://localhost:8080) in your browser to confirm Tomcat is running.

### 2. Deploy the WAR File
- **Stop Tomcat:**
  - Open Windows Services, find **Apache Tomcat 9.0**, and stop it.
- **Copy your WAR file** (e.g., `RMIASSIGNMENT.war`) to:
  ```
  C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\
  ```
- **Start Tomcat** again from Services.
-  Tomcat will auto-extract and deploy your application.

### 3. Access the Application
- Open a browser and go to:
  ```
  http://localhost:8080/RMIASSIGNMENT_war_exploded/
  ```
- **Tip:** To make your app load at the root path (`/`), rename your WAR file to `ROOT.war` before copying it to the `webapps` folder.

### 4. Open Port 8080 in Windows Firewall
- Open **Windows Defender Firewall with Advanced Security**.
- Add a new **Inbound Rule**:
    - Type: **Port**
    - Protocol: **TCP**, Port: **8080**
    - Action: **Allow the connection**
    - Apply to all profiles
    - Name it (e.g., "Tomcat 8080")
-  This allows other devices on your network to access the Tomcat server on port 8080.

---

## Demo and Screenshots

> **NB:** `After running the app, You should be able to test the interfaces and obtain results as shown in the following screenshots.`

### 1. Fruit Engine Server Running  
![Fruit Engine Server Running](https://raw.githubusercontent.com/EngineerClout/JAVARMISERVLETS/refs/heads/ft-servlets/images/GeneratingReceiptScreenOneAddingFruitToCart.png)
*Screenshot: Generating a receipt screen, Specifying the fruit to add to cart.*

### 2. Tomcat Web Server Initialized  
![Tomcat Web Server Initialized](https://raw.githubusercontent.com/EngineerClout/JAVARMISERVLETS/refs/heads/ft-servlets/images/GeneratingReceiptListOfItemsInCart.png)
*Screenshot: Screenshot showing All items in cart.*

### 3. Android App Demos

- **Add Fruit Screen**  
  ![Add New Fruit (Peach)](https://raw.githubusercontent.com/EngineerClout/JAVARMISERVLETS/refs/heads/ft-servlets/images/AddnewFruitCalledPeach.png)
  *Screenshot: Adding a new fruit (Peach) to the price table.*
  
  ![Trying To Add Existing Fruit](https://raw.githubusercontent.com/EngineerClout/JAVARMISERVLETS/refs/heads/ft-servlets/images/TryingToAddExistingFruit.png)
  *Screenshot: Attempting to add an existing fruit (error handling).*  

- **Update Fruit Screen**  
  ![Update Fruit Price of Peach](https://raw.githubusercontent.com/EngineerClout/JAVARMISERVLETS/refs/heads/ft-servlets/images/UpdateFruitPriceOfPeach.png)
  *Screenshot: Updating the price of Peach.*

- **Delete Fruit Screen**  
  ![Confirm Fruit Deletion](https://raw.githubusercontent.com/EngineerClout/JAVARMISERVLETS/refs/heads/ft-servlets/images/ConfirmFruitDeletion.png)
  *Screenshot: Confirming deletion of a fruit.*
  
  ![Fruit Deleted Successfully](https://raw.githubusercontent.com/EngineerClout/JAVARMISERVLETS/refs/heads/ft-servlets/images/FruitDeletedSuccessfully.png)
  *Screenshot: Fruit deleted successfully message.*

- **Query Fruit and Calculate Cost**  
  ![Calculate Cost Of Fruit](https://raw.githubusercontent.com/EngineerClout/JAVARMISERVLETS/refs/heads/ft-servlets/images/CalculateCostOfFruit.png)
  *Screenshot: Querying fruit price and calculating cost.*

- **Print Receipt**  
  ![Generated Final Receipt](https://raw.githubusercontent.com/EngineerClout/JAVARMISERVLETS/refs/heads/ft-servlets/images/GeneratedFinalReceipt.png)
  *Screenshot: Generated receipt showing cost, amount given, change, and cashier.*

---

## Development Diary

- Day 1: We reviewed the assignment and planned the architecture.
- Day 2: Set up the backend and implemented the core RMI classes.
- Day 3: Added servlet integration and tested RMI locally.

![RMI Engine And Server Logs](https://raw.githubusercontent.com/EngineerClout/JAVARMISERVLETS/refs/heads/ft-servlets/images/ANDROIDAPP_RMIAPPLOGS.png)

  *Screenshot: Screenshot shwoing logs from RMI ENGINE AND SERVER Logs DURING THE DEVELOPEMNT..*
- Day 4: Built the Android app UI and network module.

![Android App Logs](https://raw.githubusercontent.com/EngineerClout/JAVARMISERVLETS/refs/heads/ft-servlets/images/FruitServiceEngine_AndroidLogCat.png)

  *Screenshot: Screenshot shwoing logs from ANDROID APP DURING THE DEVELOPEMNT..*
- Day 5: Integrated client and server, tested all features.

![Final Deployment](https://raw.githubusercontent.com/EngineerClout/JAVARMISERVLETS/refs/heads/ft-servlets/images/FinalDeployment.png       )

  *Screenshot: Screenshot shwoing Testing the final deployment to TomCat 9.0 server Locally..*
- Day 6: Finalized documentation and prepared the demo.

---

## References Used For this Assignment

- Coulouris, G., Dollimore, J., & Kindburg, T. (2005). Distributed Systems: Concepts and Design (4th or 5th ed.). Addison Wesley.
- Tanenbaum, A. S., & van Steen, M. (2007). Distributed Systems: Principles and Paradigms (2nd ed.). Pearson Education International/Prentice-Hall International.
- Cornell University Library. (n.d.). APA Citation Style. http://www.library.cornell.edu/resrch/citmanage/apa
- APA Style. (n.d.). Basics of APA Style Tutorial. http://www.apastyle.org/learn/tutorials/basics-tutorial.aspx

---

## Submission

All source code and documentation are available on GitHub:  
[GitHub Repository – Fruit Service Engine](https://github.com/EngineerClout/JAVARMISERVLETS.git)

---
[PlDb]: <https://github.com/EngineerClout/JAVARMISERVLETS.git>
[PlGh]: <https://github.com/EngineerClout/JAVARMISERVLETS.git>
