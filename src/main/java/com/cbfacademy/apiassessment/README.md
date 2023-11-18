# FinTech Tycoon REST API GAME
A resource management game which requires the user to grow their finTech company to reach IPO status. 

## Introduction


## Game Rules
The purpose of this REST API game is to acquire IPO status for your finTech company. The following parameters are required in order to reach IPO status:
- Company Revenue of at least **£5,000,000**
- At least **30** employees
- At least **3** departments 
- A customer base of at least **10,000** customers 
- A company product XP of at least **30** XP

You will have 20 turns to reach IPO status and failing to do so by turn 20 will result in a GAME OVER!

## Game Actions
Within each turn there are 3 actions that you can take: 
- **Crowd fund** - This results in a £500,000 increase in revenue but can only be done once per turn


- **Add employees** - This adds a specific number of employees (determined by the user) to the company provided that the company has sufficient funds. The cost of each employee is £5000, you cannot add more than employees in each add employee action


- **Remove employees** - This removes a specific number of employees (determined by the user) from the company to generate more revenue. With each employee removal, £5000 is generated. Beware though, as removing 10 employees will also result in the removal of a department


- **Add department** - This adds a single department to the company but requires at least 10 employees 


- **Research and Development** - This adds 2 XP to the product XP of the company. This costs £50,000 but adds to the company's customer base. If the product XP is a multiple of 10, 500 customers are added to the customer base. If the product XP is maxed out (30) this will add 1000 to the customer base. This costs £50,000


- **Marketing** - This adds 1000 customers to the customer base. Having a large customer base is important for reaching IPO status but also to generate revenue as in each advance turn action. Extra revenue is generated (£5 per customer). This costs £10,000


- **Investment** - There are two investment options: Sniper and Passive. Both will randomly generate an amount of revenue which will be gained or lost. The sniper is a higher risk investment as the maximum that can be lost or won is £500,000, whereas a passive investment can result in a win or loss of £100,000. 


- **Advance turn** - This action advances the game turn to the next turn and the company into the next month. This results in a customer revenue boost (£5 per customer) and resets the investment and crowd-fund actions so that they can be used in the upcoming turn. Additionally, a random event is triggered (more on that in the section below)




<details> <br />
<summary> Click Me!</summary>
There are additional cheat code actions that you can take: <br />
- Money - This sets the company revenue to £9,999,999, making achieving IPO status much easier <br />
- Motherlode - This sets all the company variables to that required for IPO status, making achieving IPO status even easier!
</details>


### Documentation - Game Action Endpoints
The above game actions are taken by calling specific endpoints in the API documentation. Please refer to the API documentation to see details about all the API endpoints in the following swagger documentation URL:
http://127.0.0.1:8080/swagger-ui/index.html


## Random Events
These are events that are have a major influence on the company and are triggered when the advance action is taken.
There are 5 possible events that can be triggered:
1. **No Event** - This event has neither a negative nor a positive effect on the company
2. **Economic Boom** - This event results in a **10% increase** in company revenue
3. **Economic Downturn** - This event results in a **15% reduction** in company revenue
4. **Social Media Viral Event** - This results in a **1500 increase** in customer base and a **2% increase** in revenue
5. **Cyber-Security Leak** - This results in a **500** reduction in customers and a **5%** reduction in revenue

## Getting Started

### Prerequisites
Before you begin, make sure you have the following installed:

1. [JDK 17](https://learn.microsoft.com/en-gb/java/openjdk/download#openjdk-17) (or higher)

2. [Git](https://git-scm.com/downloads)

3. [Visual Studio Code](https://code.visualstudio.com/Download)
    1. [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
    2. [Spring Boot Extension Pack](https://marketplace.visualstudio.com/items?itemName=vmware.vscode-boot-dev-pack)

4. [Postman](https://learning.postman.com/docs/getting-started/installation/installation-and-updates/)

Also make sure you have accounts for the following:

1. [GitHub](https://github.com/signup)


### Steps for Set Up
1. point about the filePath
2. http://127.0.0.1:8080 (postman)
3. Run the application in your preferred IDE - **BE AWARE** - If using IntelliJ you will have to change the relative filePath in the gameRepository class to the following: /src/main/game-data.json)

5. Call the get company or get games endpoints to have an overview of your starting point it should look like this: 
- From this point, you need to refer to the API documentation to discover all the API endpoints at your disposal.


