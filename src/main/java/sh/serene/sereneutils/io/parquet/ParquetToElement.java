package sh.serene.sereneutils.io.parquet;

import org.apache.spark.sql.Row;
import scala.Tuple2;
import scala.collection.Iterator;
import sh.serene.sereneutils.model.epgm.ElementId;
import sh.serene.sereneutils.model.epgm.PropertyValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Common methods used by ParquetToElement classes
 */
public abstract class ParquetToElement {

    /**
     * Get identifier string from row
     *
     * @param row   spark dataset row
     * @return      element identifier string
     */
    protected ElementId getId(Row row) {
        return row.getAs(ParquetConstants.IDENTIFIER);
    }

    /**
     * get properties from row
     *
     * @param row   spark dataset row
     * @return      element properties
     */
    protected Map<String,PropertyValue> getProperties(Row row) {
        Map<String,PropertyValue> properties = new HashMap<>();
        scala.collection.immutable.Map<String,String> data = row.getAs("data");
        Iterator<Tuple2<String,String>> iterator = data.iterator();
        while (iterator.hasNext()) {
            Tuple2<String,String> tuple = iterator.next();
            properties.put(tuple._1(), PropertyValue.create(tuple._2()));
        }

        return properties;
    }

    /**
     * get label from row
     *
     * @param row   spark dataset row
     * @return      element label
     */
    protected String getLabel(Row row) {
        return ((Row) row.getAs(ParquetConstants.META)).getAs(ParquetConstants.LABEL);
    }

    /**
     * get graphID list from row
     *
     * @param row   spark dataset row
     * @return      list of graphs that element is contained in
     */
    protected List<ElementId> getGraphs(Row row) {
        Row meta = row.getAs(ParquetConstants.META);
        return meta.getList(meta.fieldIndex(ParquetConstants.GRAPHS));
    }

}