package sh.serene.stellarutils.io.impl.spark.parquet;

import sh.serene.stellarutils.entities.VertexCollection;
import sh.serene.stellarutils.entities.ElementId;
import sh.serene.stellarutils.entities.PropertyValue;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

import java.util.List;
import java.util.Map;

/**
 * Map function from Row (as read from parquet) to VertexCollection
 */
class ParquetToVertex extends ParquetToElement implements MapFunction<Row,VertexCollection> {

    @Override
    public VertexCollection call(Row row) {
        ElementId id = getId(row);
        Map<String,PropertyValue> properties = getProperties(row);
        String label = getLabel(row);
        List<ElementId> graphs = getGraphs(row);
        return VertexCollection.create(id, properties, label, graphs);
    }

}