--offer is an entity 

CREATE TABLE offer
(
    offer_id    INT          NOT NULL,
    offer_name  VARCHAR(100) NOT NULL,
    description TEXT,
    CONSTRAINT PK_Offer PRIMARY KEY  (offer_id)
);

CREATE TABLE offer_attributes
(
    offer_id    INT          NOT NULL, 
    attr_name   VARCHAR(100) NOT NULL, 
    attr_val    CUSTOMER_TYPE,
    description TEXT,
    CONSTRAINT PK_OfferAttr       PRIMARY KEY  (offer_id, attr_name), 
    CONSTRAINT FK_OfferAttr_Offer FOREIGN KEY  (offer_id) REFERENCES offer (offer_id)  
);

CREATE TABLE usr
(
    usr_id    INT          NOT NULL PRIMARY KEY, 
    usr_name  VARCHAR(100) NOT NULL
);

CREATE TABLE user_offer
(
    user_name  VARCHAR(100) NOT NULL,
    offer_id   INT          NOT NULL,
    CONSTRAINT PK_UserOffer PRIMARY KEY  (offer_id, attr_name),
    CONSTRAINT FK_OfferId_Offer FOREIGN KEY  (offer_id)  REFERENCES offer (offer_id), 
    CONSTRAINT FK_UserName_Name FOREIGN KEY  (usr_name) REFERENCES usr  (usr_name) 
);
