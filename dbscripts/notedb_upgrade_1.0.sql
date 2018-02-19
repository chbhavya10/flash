CREATE TABLE `notedb`.`donations` (
  `chargeId` VARCHAR(100) NOT NULL,
  `balanceTxnId` VARCHAR(100) NOT NULL,
  `chargeAmt` INT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `createDate` DATETIME NULL,
  `destinationAcctId` VARCHAR(45) NOT NULL)
  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

ALTER TABLE `notedb`.`donations` 
ADD COLUMN `txId` BIGINT(20) NOT NULL AUTO_INCREMENT FIRST,
ADD UNIQUE INDEX `txId_UNIQUE` (`txId` ASC);


USE `notedb`;
CREATE 
     OR REPLACE ALGORITHM = UNDEFINED 
    DEFINER = `snote`@`%` 
    SQL SECURITY DEFINER
VIEW `vw_organizationinfo` AS
    SELECT 
        `o`.`OrganizationId` AS `OrganizationId`,
        `o`.`OrganizationName` AS `OrganizationName`,
        `o`.`Address1` AS `Address1`,
        `o`.`Address2` AS `Address2`,
        `oi`.`Phone` AS `Phone`,
        `ct`.`CityId` AS `CityId`,
        `ct`.`CityName` AS `CityName`,
        `st`.`StateId` AS `StateId`,
        `st`.`StateName` AS `StateName`,
        `c`.`CountryID` AS `CountryID`,
        `c`.`CountryName` AS `CountryName`,
        `o`.`ZipCode` AS `ZipCode`,
        `oi`.`Hours` AS `Hours`,
        `oi`.`GeneralInfo` AS `GeneralInfo`,
        `oi`.`Website` AS `Website`,
        `oi`.`PrimaryEmail` AS `PrimaryEmail`,
        `oi`.`FacebookLink` AS `FacebookLink`,
        `o`.`OrgImage` AS `OrgImage`,
        `d`.`Denomination` AS `Denomination`,
        `oi`.`Pastor1Bio` AS `Pastor1Bio`,
        `oi`.`Pastor2Bio` AS `Pastor2Bio`,
        `o`.`accountId` AS `StripeAcctId`
    FROM
        (((((`organizationinfo` `oi`
        JOIN `organization` `o` ON ((`o`.`OrganizationId` = `oi`.`OrganizationId`)))
        JOIN `city` `ct` ON ((`ct`.`CityId` = `o`.`CityID`)))
        JOIN `state` `st` ON ((`st`.`StateId` = `o`.`StateId`)))
        JOIN `country` `c` ON ((`c`.`CountryID` = `o`.`CountryID`)))
        JOIN `denomination` `d` ON ((`d`.`DenominationID` = `o`.`DenominationId`)));
        
USE `notedb`;
DROP procedure IF EXISTS `update_orgInfo`;

DELIMITER $$
USE `notedb`$$
CREATE DEFINER=`snote`@`%` PROCEDURE `update_orgInfo`(
IN pOrganizationId INT,
IN pWebsite VARCHAR(150),
IN pPrimaryEmail VARCHAR(100),
IN pGeneralInfo varchar(500),
IN pHours VARCHAR(150),
in pFacebookLink varchar(150),
in pPastor1Bio varchar(500),
in pPastor2Bio varchar(500)
)
BEGIN
UPDATE `OrganizationInfo` SET `Website`=pWebsite,`PrimaryEmail`=pPrimaryEmail,`GeneralInfo`=pGeneralInfo,`Hours`=pHours,
`FacebookLink`=pFacebookLink,`Pastor1Bio`=pPastor1Bio,`Pastor2Bio`=pPastor2Bio WHERE `OrganizationId`=pOrganizationId;
COMMIT;
END$$

DELIMITER ;
