# warehouseApplication
A school project to be used in two courses: User Interface Programming and Data Management Systems. Language Java.

## Little background

I work (beside my studies) in an organization that has products around premises (owned and leased). Some are in stock or stored in few rooms, Some are at office and most of them are in service (installed). Idea is to get a database that can hold information about whereabouts of individual serialized devices (not supply items / nuts and bolts) and quantity of supply items (nuts and bolts) in warehouses.

## User stories
As a user I want to:
- be able to add warehouses / rooms / spaces to database
- remove warehouses from database
- search by product name
- search by product#
- search by product location if installed in field / in production
- search by quantity
- add an indentified product to warehouse table
- remove an item from warehouse
- mark the location to an indentified product if in service
- add supplies to warehouse table
- list products that have lease expiring

## Programming language and database
Since in the User Interface course Java and JavaFX is used, it'll be the language used in this project.

Data management course uses MariaDB and course project will be a relational database, so having a simple MariaDB compatible api is the first target in this project.

![ER-diagram](/documentation/ER-diagram_v02.png)

![Relational Schema](/documentation/RelationalSchema_v02.png)
