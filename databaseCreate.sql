CREATE DATABASE warehouseApplicationDB
CHARACTER SET = 'utf8'
COLLATE = 'utf8_swedish_ci';

USE warehouseApplicationDB;

CREATE TABLE Room
(
  building VARCHAR(50) NOT NULL,
  roomID VARCHAR(10) NOT NULL,
  campus VARCHAR(50) NOT NULL,
  PRIMARY KEY (roomID)
);

CREATE TABLE StoreRoom
(
  storeRoomID VARCHAR(50) NOT NULL,
  grossFloorArea FLOAT,
  roomHeight FLOAT,
  PRIMARY KEY (storeRoomID)
);

CREATE TABLE StoragePosition
(
  positionID INT NOT NULL AUTO_INCREMENT,
  storeRoomID VARCHAR(50) NOT NULL,
  PRIMARY KEY (positionID),
  FOREIGN KEY (storeRoomID) REFERENCES StoreRoom(storeRoomID)
);

CREATE TABLE Accessory
(
  name VARCHAR(80) NOT NULL,
  accessoryID INT NOT NULL AUTO_INCREMENT,
  quantity INT NOT NULL,
  productDesc VARCHAR(90),
  manufacturer VARCHAR(50),
  productNo VARCHAR(80),
  PRIMARY KEY (accessoryID)
);

CREATE TABLE Leasing
(
  leaseTime INT NOT NULL,
  leasingCompany VARCHAR(80) NOT NULL,
  leaseID INT NOT NULL AUTO_INCREMENT,
  contractNo VARCHAR(80) NOT NULL,
  leaseStartDate DATE NOT NULL,
  PRIMARY KEY (leaseID)
);

CREATE TABLE AccessoryPos
(
  positionID INT NOT NULL,
  accessoryID INT NOT NULL,
  FOREIGN KEY (positionID) REFERENCES StoragePosition(positionID),
  FOREIGN KEY (accessoryID) REFERENCES Accessory(accessoryID)
);

CREATE TABLE Orders
(
  orderID INT NOT NULL AUTO_INCREMENT,
  purchaseDate DATE NOT NULL,
  orderNo VARCHAR(70) NOT NULL,
  deliveryDate DATE NOT NULL,
  PRIMARY KEY (orderID)
);

CREATE TABLE Vendors
(
  vendorID INT NOT NULL AUTO_INCREMENT,
  companyName VARCHAR(80) NOT NULL,
  orderID INT NOT NULL,
  PRIMARY KEY (vendorID),
  FOREIGN KEY (orderID) REFERENCES Orders(orderID)
);

CREATE TABLE SerializedProduct
(
  productNo VARCHAR(80),
  serialNo INT NOT NULL,
  manufacturer VARCHAR(80) NOT NULL,
  name VARCHAR(80) NOT NULL,
  warranty INT NOT NULL,
  productID INT NOT NULL AUTO_INCREMENT,
  isOwned INT NOT NULL,
  isInProduction INT NOT NULL,
  leaseID INT,
  roomID VARCHAR(10),
  positionID INT,
  orderID INT,
  PRIMARY KEY (productID),
  FOREIGN KEY (leaseID) REFERENCES Leasing(leaseID),
  FOREIGN KEY (roomID) REFERENCES Room(roomID),
  FOREIGN KEY (positionID) REFERENCES StoragePosition(positionID),
  FOREIGN KEY (orderID) REFERENCES Orders(orderID)
);
