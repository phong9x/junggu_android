package com.trams.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class GreenDAOGenerator {
    private static final String PROJECT_DIR = System.getProperty("user.dir").replace("\\", "/");
    private static final String OUT_DIR = PROJECT_DIR + "/app/src/main/java";

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.trams.joonggu_nubigo.dao");

        addTables(schema);

        new DaoGenerator().generateAll(schema, OUT_DIR);
    }

    /**
     * Create tables and the relationships between them
     */
    private static void addTables(Schema schema) {
        /* entities */
        Entity accessibility = addAccessibility(schema);
        Entity category = addCategory(schema);
        Entity facility = addFacility(schema);
        Entity field = addField(schema);
        Entity notice = addNotice(schema);
        Entity store = addStore(schema);
        Entity user = addUser(schema);
        Entity version = addVersion(schema);

        /* properties */
        Property categoryForStore = store.addLongProperty("catId").notNull().getProperty();
        Property userForNotice = notice.addLongProperty("userId").notNull().getProperty();
        Property userForStore = store.addLongProperty("userId").notNull().getProperty();

       /* Property userIdForUserDetails = userDetails.addLongProperty("userId").notNull().getProperty();
        Property userDetailsIdForUser = user.addLongProperty("detailsId").notNull().getProperty();
        Property userDetailsIdForPhoneNumber = phoneNumber.addLongProperty("detailsId").notNull().getProperty();*/

        /* relationships between entities */
        category.addToMany(store, categoryForStore, "stores");
        store.addToOne(category, categoryForStore, "categories");
        store.addToOne(user, userForStore, "users");
        notice.addToOne(user, userForNotice, "users");
       /* userDetails.addToOne(user, userIdForUserDetails, "user");    // one-to-one (user.getDetails)
        user.addToOne(userDetails, userDetailsIdForUser, "details"); // one-to-one (user.getUser)

        ToMany userDetailsToPhoneNumbers = userDetails.addToMany(phoneNumber, userDetailsIdForPhoneNumber);
        userDetailsToPhoneNumbers.setName("phoneNumbers"); // one-to-many (userDetails.getListOfPhoneNumbers)*/

    }

    /**
     * Create accessibility's Properties
     *
     * @return DBAccessibility entity
     */
    private static Entity addAccessibility(Schema schema) {
        Entity accessibility = schema.addEntity("Accessibility");
        accessibility.addIdProperty().primaryKey().autoincrement().notNull();
        accessibility.addStringProperty("name").notNull();
        accessibility.addStringProperty("description").notNull();
        accessibility.addDateProperty("createDate");
        accessibility.addDateProperty("updateDate");
        accessibility.addIntProperty("selected");
        return accessibility;
    }

    /**
     * Create category's Properties
     *
     * @return DBCategory entity
     */
    private static Entity addCategory(Schema schema) {
        Entity category = schema.addEntity("Category");
        category.addIdProperty().primaryKey().autoincrement().notNull();
        category.addStringProperty("catName").notNull();
        category.addStringProperty("description");
        category.addIntProperty("parentId").notNull();
        category.addStringProperty("image");
        category.addStringProperty("etc");
        category.addDateProperty("createDate");
        category.addDateProperty("updateDate");
        return category;
    }


    /**
     * Create facility's Properties
     *
     * @return DBFacility entity
     */

    private static Entity addFacility(Schema schema) {
        Entity facility = schema.addEntity("Facility");
        facility.addIdProperty().primaryKey().autoincrement().notNull();
        facility.addStringProperty("name").notNull();
        facility.addStringProperty("description").notNull();
        facility.addDateProperty("createDate");
        facility.addDateProperty("updateDate");
        return facility;
    }

    /**
     * Create field's Properties
     *
     * @return DBField entity
     */

    private static Entity addField(Schema schema) {
        Entity field = schema.addEntity("Field");
        field.addIdProperty().primaryKey().autoincrement().notNull();
        field.addStringProperty("name").notNull();
        field.addStringProperty("description").notNull();
        field.addDateProperty("createDate");
        field.addDateProperty("updateDate");
        return field;
    }

    /**
     * Create notice's Properties
     *
     * @return DBNotice entity
     */

    private static Entity addNotice(Schema schema) {
        Entity notice = schema.addEntity("Notice");
        notice.addIdProperty().primaryKey().autoincrement().notNull();
        //notice.addIntProperty("userId").notNull();
        notice.addStringProperty("title").notNull();
        notice.addStringProperty("content");
        notice.addIntProperty("noticeType");
        notice.addDateProperty("createDate");
        notice.addDateProperty("updateDate");
        return notice;
    }


    /**
     * Create store Properties
     *
     * @return DBStore entity
     */
    private static Entity addStore(Schema schema) {
        Entity store = schema.addEntity("Store");
        store.addIdProperty().primaryKey().autoincrement().notNull();
        //userDetails.addLongProperty("id").notNull().unique().primaryKey().autoincrement();
        //store.addIntProperty("userId");
        store.addStringProperty("name").notNull();
        //store.addIntProperty("catId").notNull();
        store.addStringProperty("tag");
        store.addStringProperty("serviceHours");
        store.addStringProperty("holiday");
        store.addStringProperty("buildingForm");
        store.addStringProperty("floor");
        store.addStringProperty("facilityList");
        store.addStringProperty("representative");
        store.addStringProperty("phone");
        store.addStringProperty("address");
        store.addDateProperty("monitoringDate");
        store.addStringProperty("monitoringMan");
        store.addStringProperty("monitoringManPhone");
        store.addStringProperty("fieldList");
        store.addStringProperty("imageBaseAttach");
        store.addStringProperty("imageExtendAttach");
        store.addStringProperty("grade");
        store.addStringProperty("accessibilityList");
        store.addStringProperty("longitude");
        store.addStringProperty("latitude");
        store.addDateProperty("createDate");
        store.addDateProperty("updateDate");
        store.addStringProperty("otherInfo");
        store.addIntProperty("isDelete");

        return store;
    }

    /**
     * Create user Properties
     *
     * @return DBUser entity
     */
    private static Entity addUser(Schema schema) {
        Entity user = schema.addEntity("User");
        user.addIdProperty().primaryKey().autoincrement().notNull();
        user.addStringProperty("username").notNull();
        user.addStringProperty("password").notNull();
        user.addStringProperty("nickname");
        user.addStringProperty("fullname");
        user.addIntProperty("role").notNull();
        user.addStringProperty("sex");
        user.addStringProperty("phone");
        user.addStringProperty("email");
        user.addIntProperty("age");
        user.addStringProperty("scrap");
        user.addDateProperty("createDate").notNull();
        user.addDateProperty("updateDate").notNull();
        return user;
    }

    /**
     * Create version Properties
     *
     * @return DBVersion entity
     */
    private static Entity addVersion(Schema schema) {
        Entity version = schema.addEntity("Version");
        version.addIdProperty().primaryKey().autoincrement().notNull();
        version.addStringProperty("name").notNull();
        version.addStringProperty("status").notNull();
        version.addStringProperty("etc");
        version.addDateProperty("createDate").notNull();
        version.addDateProperty("updateDate").notNull();
        return version;
    }
}
