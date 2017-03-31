package ntu.ce2006.swensens.hdbdesirabilityapp.search.result;

import java.util.Comparator;
import java.util.HashMap;

import ntu.ce2006.swensens.hdbdesirabilityapp.search.result.sort.SortOrder;

/**
 * Created by Swensens on 20/03/17.
 */

public class Flat implements Comparable<Flat> {

    // Score of the result
    private double score;

    // Address of the result
    private String address;

    private String streetName;

    private String block;

    private String town;

    private String size;

    // Price of the result
    private double price;

    // Area of the result
    private double area;

    // Amenities of the result in format {name, distance}
    private HashMap<String, Integer> amenities;

    public static class Builder {
        // Required Parameters
        private double score = 0;
        private String streetName;
        private String block;
        private String town;
        private String address;
        private String size;
        private double price;
        private double area;

        // Optional Parameters
        private HashMap<String, Integer> amenities = new HashMap<>();

        public Builder(String block, String streetName, String town, String address, String size, double price, double area) {
            this.block = block;
            this.streetName = streetName;
            this.town = town;
            this.address = address;
            this.size = size;
            this.price = price;
            this.area = area;
        }

        // TODO depends on when you want to sort the amenities from the API
        /*
        public Builder amenities(String Name)
        */
        public Builder amenities(HashMap<String, Integer> amenities) {
            this.amenities = amenities;
            return this;
        }

        public Builder score(double score) {
            this.score = score;
            return this;
        }

        public Flat build() {
            return new Flat(this);
        }
    }

    private Flat(Builder builder) {
        score = builder.score;
        block = builder.block;
        streetName = builder.streetName;
        town = builder.town;
        address = builder.address;
        size = builder.size;
        price = builder.price;
        area = builder.area;
        amenities = builder.amenities;
    }

    public String getBlock() {
        return block;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getTown() {
        return town;
    }

    public void setAmenities(HashMap<String, Integer> amenities) {
        this.amenities = amenities;
    }

    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Returns the score
     *
     * @return score of the result
     */
    public double getScore() {
        return score;
    }

    /**
     * Returns the address
     *
     * @return address of the result
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns the size of the flat
     *
     * @return size of the flat
     */
    public String getSize() {
        return size;
    }

    /**
     * Returns the price
     *
     * @return price of the result
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the area
     *
     * @return area of the result
     */
    public double getArea() {
        return area;
    }

    /**
     * Returns the amenities in format {name, distance}
     *
     * @return amenities of result in hashmap
     */
    public HashMap<String, Integer> getAmenities() {
        return amenities;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Score: " + String.format("%.2f", score) + "/10" + System.getProperty("line.separator")
                + "Address: " + address + System.getProperty("line.separator")
                + "Price: S$" + String.format("%.2f", price) + System.getProperty("line.separator")
                + "Size: " + size + System.getProperty("line.separator")
                + "Amenities: ");
        // Amenities
        int index = 0;
        for (String amenitiesName : amenities.keySet()) {
            if (index == amenities.size() - 1) {
                sb.append(amenities.get(amenitiesName) + " " + amenitiesName);
                break;
            }
            sb.append(amenities.get(amenitiesName) + " " + amenitiesName + ", ");
            index++;
        }
        return sb.toString();
    }

    // DEFAULT (By Score) UNDERNEATH ARE SORTING
    // TODO (TIP): Use Collections.sort(arrayList<Flat>, Flat.DefaultComparator());
    @Override
    public int compareTo(Flat otherFlat) {
        double compareScore = this.score - otherFlat.getScore();
        if (compareScore < 0) {
            return -1;
        } else if (compareScore > 0) {
            return 1;
        }
        return 0;
    }

    // Other sort order
    public int compareTo(Flat otherFlat, SortOrder sortOrder) {
        switch (sortOrder) {
            case PRICE_ASCENDING:
                return priceAscendingCompare(otherFlat);
            case PRICE_DESCENDING:
                return priceDescendingCompare(otherFlat);
            case DEFAULT:
                return compareTo(otherFlat);
        }
        return 0;
    }

    private int priceAscendingCompare(Flat otherFlat) {
        if (this.price > otherFlat.price) {
            return 1;
        } else if (this.price < otherFlat.price) {
            return -1;
        }
        return 0;
    }

    private int priceDescendingCompare(Flat otherFlat) {
        if (this.price > otherFlat.price) {
            return -1;
        } else if (this.price < otherFlat.price) {
            return 1;
        }
        return 0;
    }

    public static Comparator<Flat> PriceAscendingComparator() {
        return new Comparator<Flat>() {
            @Override
            public int compare(Flat flat1, Flat flat2) {
                return flat1.compareTo(flat2, SortOrder.PRICE_ASCENDING);
            }
        };
    }

    public static Comparator<Flat> PriceDescendingComparator() {
        return new Comparator<Flat>() {
            @Override
            public int compare(Flat flat1, Flat flat2) {
                return flat1.compareTo(flat2, SortOrder.PRICE_DESCENDING);
            }
        };
    }

    public static Comparator<Flat> DefaultComparator() {
        return new Comparator<Flat>() {
            @Override
            public int compare(Flat flat1, Flat flat2) {
                return flat1.compareTo(flat2, SortOrder.DEFAULT);
            }
        };
    }
}