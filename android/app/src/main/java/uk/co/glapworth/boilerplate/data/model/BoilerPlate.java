package uk.co.glapworth.boilerplate.data.model;

/**
 * Created by glapworth on 07/06/16.
 */
public class BoilerPlate implements Comparable<BoilerPlate> {

    public Profile profile;

    @Override
    public int compareTo(BoilerPlate another) {
        return profile.name.compareTo(another.profile.name);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        BoilerPlate bp = (BoilerPlate) o;

        return !(profile != null ? !profile.equals(bp.profile) : bp.profile != null);
    }

    @Override
    public int hashCode() {
        int result = profile != null ? profile.hashCode() : 0;
        return result;
    }

}
