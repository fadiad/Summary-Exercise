package TestEntities;

import mainB.annotations.primaryKey;

public class simpleEntity
{
    @primaryKey
    int id;
    String value;
    public simpleEntity(int id, String value)
    {
        this.id=id;
        this.value=value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        simpleEntity that = (simpleEntity) o;

        if (id != that.id) return false;
        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "simpleEntity{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }

   public simpleEntity()
    {

    }
}