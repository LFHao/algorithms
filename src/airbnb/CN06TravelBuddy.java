package airbnb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CN06TravelBuddy {
    private Set<String> myFavCities;
    private Map<String, Set<String>> myFriendsFavCities;

    public CN06TravelBuddy(Set<String> myFavCities, Map<String, Set<String>> myFriendsFavCities) {
        this.myFavCities = myFavCities;
        this.myFriendsFavCities = myFriendsFavCities;
    }

    public List<Buddy> getMyTravelBuddies() {
        List<Buddy> myTravelBuddies = new ArrayList<>();
        int myFavLen = myFavCities.size();

        for (String buddyName : myFriendsFavCities.keySet()) {
            Set<String> buddyFavCities = myFriendsFavCities.get(buddyName);
            buddyFavCities.removeAll(myFavCities);
            double similarity = 1 - (double)(buddyFavCities.size() - myFavLen) / myFavLen;
            myTravelBuddies.add(new Buddy(buddyName, similarity, buddyFavCities));
        }

        Collections.sort(myTravelBuddies);
        return myTravelBuddies;
    }

    public List<String> recommendCities(int k) {
        List<Buddy> myTravelBuddies = getMyTravelBuddies();
        List<String> recomCities = new ArrayList<>();

        int i = 0;
        while (k > 0 && i < myTravelBuddies.size()) {
            Set<String> diff = myTravelBuddies.get(i).diff;

            if (diff.size() <= k) {
                recomCities.addAll(diff);
                k -= diff.size();
                i++;
            } else {
                // till the last buddy that can be added to recommendCities list
                Iterator<String> iterator = diff.iterator();
                while (k > 0 && iterator.hasNext()) {
                    recomCities.add(iterator.next());
                    k--;
                }
            }
        }

        return recomCities;
    }

    public static void main(String[] args) {
        Set<String> myFavCities = new HashSet<>();
        myFavCities.add("a");
        myFavCities.add("b");
        myFavCities.add("c");
        myFavCities.add("d");

        Set<String> b1Fav = new HashSet<>();
        b1Fav.add("a");
        b1Fav.add("b");
        b1Fav.add("e");
        b1Fav.add("f");

        Set<String> b2Fav = new HashSet<>();
        b2Fav.add("a");
        b2Fav.add("c");
        b2Fav.add("d");
        b2Fav.add("g");

        Map<String, Set<String>> map = new HashMap<>();
        map.put("b1", b1Fav);
        map.put("b2", b2Fav);

        CN06TravelBuddy rc = new CN06TravelBuddy(myFavCities, map);
        System.out.println("travel buddies are" + rc.getMyTravelBuddies().toString());

        System.out.println("recommend cities are " + rc.recommendCities(2));
    }

    class Buddy implements Comparable<Buddy> {
        String name;
        double similarity;
        Set<String> diff;

        Buddy(String name, double similarity, Set<String> diff) {
            this.name = name;
            this.similarity = similarity;
            this.diff = diff;
        }

        @Override
        public int compareTo(Buddy other) {
            return (int)(other.similarity - this.similarity);
        }

        @Override
        public String toString() {
            return name + ", " + similarity + ", " + diff.toString();
        }
    }
}


