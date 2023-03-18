CREATE TABLE IF NOT EXISTS campaign(
                                       campaign_id int NOT NULL AUTO_INCREMENT,
                                       card_program_id int NOT NULL,
                                       reward_rate int,
                                       title varchar(255),
    min_dollar_spent double DEFAULT 0.0,
    mcc varchar(255),
    is_active boolean,
    start_date DATETIME,
    end_date DATETIME,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    custom_category varchar(255),
    CONSTRAINT PRIMARY KEY(campaign_id)
    );

CREATE TABLE IF NOT EXISTS user_subscription(
                                                subscription_id int NOT NULL AUTO_INCREMENT,
                                                campaign_id int NOT NULL,
                                                user_id int NOT NULL,
                                                platform varchar(255),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY(subscription_id),
    CONSTRAINT FK_campaigns_user_subscription FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id)
    );

CREATE TABLE IF NOT EXISTS notification(
                                           notification_id int NOT NULL AUTO_INCREMENT,
                                           campaign_id int NOT NULL,
                                           message varchar(512),
    platform varchar(255),
    title varchar(255),
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY(notification_id),
    CONSTRAINT FK_campaigns_notifications FOREIGN KEY (campaign_id) REFERENCES campaign(campaign_id)
    );