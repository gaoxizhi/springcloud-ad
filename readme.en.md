## Advertising system based on Spring Cloud

### project background
> needs to complete an advertising system for the media and advertisers to place and display ads
### destination
> is the same as the project background. In fact, as long as understand the advertising system can achieve the function, also know the background and purpose

Select the microservices development framework
> reduces coupling between service functions, makes developer assignment easier, and so on

### design ideas
> must first be about how and why tables are designed, about the hierarchical table structure, and about what data is stored in each table. After the release system, is to achieve the table data to add, delete, modify and check; Finally, the retrieval system loads the data in the data table into the system, constructs the inverted index, and realizes the efficient retrieval service.

### Advertising system main points

- SpringCloud
- advertising search
- microservices architecture
    - SpringCloud
        - Eureka service governance
    - implement service registration and discovery
        - Zuul gateway
    - implement routing forwarding and request information recording (custom filter)
        - Feign
    - kafka message queue component
- resolve MySQL Binlog