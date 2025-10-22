# Lost-and-found

Lost & Found Item Management System
📘 Overview

The Lost & Found Item Management System is a simple console-based Java application designed to help organizations efficiently manage lost and found items. It provides a structured, centralized system for registering, searching, and claiming items, thereby replacing traditional manual logs or whiteboard methods.

This project demonstrates key Object-Oriented Programming (OOP) principles, file handling, and collections in Java.

🎯 Problem Statement

In educational institutions, offices, and public areas, managing lost and found items is often inefficient due to manual tracking. Items remain unclaimed, and users have no centralized way to search or report them.

This system addresses that issue by providing:

Centralized item registration (lost or found)

Search and claim features

Persistent data storage through file handling

🎯 Objectives

The main goals of this project are:

Register lost and found items with full descriptive details.

Enable searching by keyword, date, or location.

Provide a secure claiming mechanism for rightful owners.

Persist item data using file storage for continuity.

Demonstrate Java programming concepts such as:

OOP principles

Collections (ArrayList)

Exception handling

File I/O

📚 System Features
Functionality	Description
Add Item	Register a new lost or found item with details such as description, date, location, and status.
Search Items	Search by keywords found in item description, date, or location.
Claim Item	Verify ownership and claim an item. Claimed items are removed from the list.
View All Items	Display all registered items (lost and found).
Data Persistence	Automatically load and save data from a text file (items.txt).
🧩 Data Model

Class: Item
Attributes:

int itemID — Unique identifier

String description — Description of the item

String date — Date of loss or discovery (DD-MM-YYYY)

String location — Where the item was lost/found

String status — Either "Lost" or "Found"

⚙️ Implementation Details
🛠️ Technologies Used

Language: Java (JDK 8 or higher)

Libraries: Java Collections Framework

Storage: File I/O using BufferedReader and BufferedWriter

Interface: Console-based (text menu system)

💡 Design Decisions

ArrayList for dynamic storage of items.

Text file for data persistence (items.txt).

Auto-incremented ID for each item.

Simple verification during claim (by description).

⚠️ Limitations

Single-user, single-threaded application.

No advanced authentication or database integration.

Basic date validation (no date parsing).

Console-based interface only.
