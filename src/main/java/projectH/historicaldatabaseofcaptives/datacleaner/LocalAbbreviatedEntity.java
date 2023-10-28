package projectH.historicaldatabaseofcaptives.datacleaner;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity(name = "local_abbreviated_entity")
@Table(name = "local_abbreviated_entity")
public class LocalAbbreviatedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @CreationTimestamp
    private Timestamp insert_time;
    private String abbr;  //abbreviation
    private String value_hu;
    private String value_en;

    private String table_column;

    private String related_table;

    public LocalAbbreviatedEntity(Long id, Timestamp insert_time, String abbr, String value_hu, String value_en, String table_column, String related_table) {
        this.id = id;
        this.insert_time = insert_time;
        this.abbr = abbr;
        this.value_hu = value_hu;
        this.value_en = value_en;
        this.table_column = table_column;
        this.related_table = related_table;
    }

    public LocalAbbreviatedEntity() {

    }

    public String getTable_column() {
        return table_column;
    }

    public void setTable_column(String table_column) {
        this.table_column = table_column;
    }

    public String getRelated_table() {
        return related_table;
    }

    public void setRelated_table(String related_table) {
        this.related_table = related_table;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(Timestamp insert_time) {
        this.insert_time = insert_time;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getValue_hu() {
        return value_hu;
    }

    public void setValue_hu(String value_hu) {
        this.value_hu = value_hu;
    }

    public String getValue_en() {
        return value_en;
    }

    public void setValue_en(String value_en) {
        this.value_en = value_en;
    }



    @Override
    public String toString() {
        return "LocalAbbreviatedEntity{" +
                "id=" + id +
                ", insert_time=" + insert_time +
                ", abbreviation='" + abbr + '\'' +
                ", value_hu='" + value_hu + '\'' +
                ", value_en='" + value_en + '\'' +
                ", column='" + table_column + '\'' +
                ", table='" + related_table + '\'' +
                '}';
    }
}
