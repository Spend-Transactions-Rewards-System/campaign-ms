<a name="readme-top"></a>


<!-- PROJECT LOGO -->
<br />
<div align="center">
<h3 align="center">ITSA G1 T3 Project B AY2022/23 Semester 2</h3>

  <p align="center">
    Campaign-Rewards Microservice
    <br />
    <a href="https://itsa-t3-upload-ms.stoplight.io/docs/upload-ms/branches/main/9ae56e8b59f9f-upload-ms"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://www.itsag1t3.com">View Demo</a>
  </p>
</div>


<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>


<!-- ABOUT THE PROJECT -->
## About The Microservice
Reward programs are becoming a popular marketing tool for banks and credit card issuers to attract and retain customers. Our application provides customers with an efficient and user-friendly platform for managing their rewards. A large number of spend transactions can be processed daily in real-time and the application is able to accept these transactions via a file upload or API request to the tenant. Campaign management is available for tenants, where they can run customisable campaigns for specific card programs that encourage user spending, while at the same time reward customers. From a customer perspective, this enhances the perceived value of these card programs offered by the tenant, helping our affiliated banks to preserve brand loyalty and expand their market share.

The Campaign-Rewards microservice handles reward earnings based on card programs and campaigns. It also handles notification to send email for campaigns. 


### Built With

* [![Spring][Spring.com]][Spring-url]
* [![MySQL][MySQL.com]][MySQL-url]
* [![AWS ECS][AWS.com]][AWS-url]



<!-- GETTING STARTED -->
## Getting Started
### Prerequisites
1. Create MySQL database instance in AWS
2. Create SQS Queue with the name: CampaignToCard
3. Create SQS Queue with the name: CardToCampaign
4. Install JDK 17
5. Install Maven 3
6. Retrieve _**Access Key ID**_ and _**Secret Access Key**_ from your IAM user


### Installation (Linux)
1. Clone the repo
   ```sh
   git clone https://github.com/cs301-itsa/project-2022-23t2-g1-t3-be-campaign-ms.git
   ```
2. Change directory into the repo
    ```sh
    cd project-2022-23t2-g1-t3-be-campaign-ms
    ```
3. Create virtual environment and install project dependencies
   ```sh
   mvn clean install
   ```
4. Create an environment file and add your environment variables
   ```sh
   nano ./.env
   ```
   Replace variables in curly braces with your own credentials
   ```txt
   JPA_DDL_CONFIG={create/update/none}
   DB_MYSQL_URL={Database URL}
   DB_MYSQL_USERNAME={Database username}
   DB_MYSQL_PASSWORD={Database password}
   AWS_REGION={AWS Region}
   AWS_ACCESS_KEY_ID={AWS Access Key}
   AWS_SECRET_ACCESS_KEY={AWS Secret Access Key}
   ```
5. Run the microservice
   ```sh
   mvn spring-boot:run 
   ```
<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

### Team Members
* Sean Tan
* Joshua Zhang
* Tan Gi Han
* Stanford
* Hafil Noer Zachiary
* Dino
* Gan Shao Hong

### Project Advisor/Mentor
* [Professor Ouh Eng Lieh](https://www.linkedin.com/in/eng-lieh-ouh/?originalSubdomain=sg)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

[AWS-url]: https://aws.amazon.com/ecs/
[AWS.com]: https://img.shields.io/badge/Amazon_AWS-232F3E?style=for-the-badge&logo=amazon-aws&logoColor=white
[Spring-url]: https://spring.io/
[Spring.com]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[MySQL-url]: https://www.mysql.com/
[MySQL.com]: https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white
