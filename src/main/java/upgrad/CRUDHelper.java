package upgrad;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import  com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Filters;

import static com.mongodb.client.model.Aggregates.*;
import com.mongodb.client.model.Sorts;

import org.bson.Document;
import java.util.Arrays;

public class CRUDHelper {

    /**
     * Display ALl products
     * @param collection
     */
    public static void displayAllProducts(MongoCollection<Document> collection) {
        System.out.println("------ Displaying All Products ------");
        FindIterable<Document> find = collection.find();
        find.cursor().forEachRemaining(doc->{
        	PrintHelper.printSingleCommonAttributes(doc);
        });
        
        // Call printSingleCommonAttributes to display the attributes on the Screen

    }

    /**
     * Display top 5 Mobiles
     * @param collection
     */
    public static void displayTop5Mobiles(MongoCollection<Document> collection) {
        System.out.println("------ Displaying Top 5 Mobiles ------");
        Document query= new Document();
        query.append("Category", "Mobiles");
        FindIterable<Document> find = collection.find(query).limit(5);
        find.cursor().forEachRemaining(doc->{
        	PrintHelper.printAllAttributes(doc);
        });
        // Call printAllAttributes to display the attributes on the Screen
    }

    /**
     * Display products ordered by their categories in Descending order without auto generated Id
     * @param collection
     */
    public static void displayCategoryOrderedProductsDescending(MongoCollection<Document> collection) {
        System.out.println("------ Displaying Products ordered by categories ------");
        Document query= new Document();
       query.append("Category", -1);
        FindIterable<Document> sort = collection.find().sort(query);
        sort.forEach(doc->{
        	PrintHelper.printAllAttributes(doc);
        });
        // Call printAllAttributes to display the attributes on the Screen
    }


    /**
     * Display number of products in each group
     * @param collection
     */

	public static void displayProductCountByCategory(MongoCollection<Document> collection) {
        System.out.println("------ Displaying Product Count by categories ------");
        // Call printProductCountInCategory to display the attributes on the Screen
        collection.aggregate(Arrays.asList(
        	      group("$Category", Accumulators.sum("count", 1)),
        	      sort(Sorts.descending("count")))).iterator().forEachRemaining(test->{
        	    	  PrintHelper.printProductCountInCategory(test);
        	      });;
  
    }

    /**
     * Display Wired Headphones
     * @param collection
     */
    public static void displayWiredHeadphones(MongoCollection<Document> collection) {
        System.out.println("------ Displaying Wired headphones ------");
        // Call printAllAttributes to display the attributes on the Screen
        FindIterable<Document> find = collection.find(Filters.eq("ConnectorType","Wired"));
        find.iterator().forEachRemaining(docs->{
        	PrintHelper.printAllAttributes(docs);
        });
    }
}