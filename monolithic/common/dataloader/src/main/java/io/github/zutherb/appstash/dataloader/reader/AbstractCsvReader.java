package io.github.zutherb.appstash.dataloader.reader;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import io.github.zutherb.appstash.shop.repository.user.model.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zuther
 */
public abstract class AbstractCsvReader<T> {

    public List<T> parseCsv() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(getClassPathFilePath(), this.getClass().getClassLoader());
        InputStreamReader ioReader = new InputStreamReader(classPathResource.getInputStream(), "UTF-8");
        CSVReader reader = new CSVReader(ioReader, ';');

        ColumnPositionMappingStrategy<T> strat = new ColumnPositionMappingStrategy<>();
        strat.setType(getDestinationClass());
        strat.setColumnMapping(getColumnMapping());
        CsvToBean<T> csv = getParser();
        return csv.parse(strat, reader);
    }

    protected abstract String getClassPathFilePath();

    protected abstract String[] getColumnMapping();

    protected abstract Class<T> getDestinationClass();


    // open csv parser is not always capable to process enum destination types, thus have to tweak a little bit
    protected CsvToBean<T> getParser() {
        return new CsvToBean<T>() {
            @Override
            protected PropertyEditor getPropertyEditor(final PropertyDescriptor desc) throws InstantiationException, IllegalAccessException {

                if (getDestinationClass().isAssignableFrom(User.class) && "categories".equalsIgnoreCase(
                        desc.getDisplayName())) {
                    return new DummyPropertyEditor() {
                        @Override
                        public Object getValue() {
                            if (StringUtils.isBlank(getAsText())) {
                                return null;
                            }
                            Set<String> result = new HashSet<>();
                            CollectionUtils.addAll(result, StringUtils.split(getAsText(), ","));
                            return result;
                        }
                    };
                }
                final Class<?> enumCandidate = desc.getWriteMethod().getParameterTypes()[0];

                if (enumCandidate.isEnum()) {
                    return new DummyPropertyEditor() {
                        @Override
                        public Object getValue() {
                            try {
                                Method valueOfMethod = enumCandidate.getMethod("valueOf", String.class);
                                return valueOfMethod.invoke(null, getAsText());
                            } catch (Exception e) {
                                throw new RuntimeException("Unable to parse enum " + enumCandidate + " from csv value "
                                        + getAsText());
                            }
                        }
                    };
                }
                return super.getPropertyEditor(desc);
            }
        };
    }


    private static class DummyPropertyEditor extends PropertyEditorSupport {
        private String sourceCsvValue;

        @Override
        public void setAsText(String sourceCsvValue) throws IllegalArgumentException {
            this.sourceCsvValue = sourceCsvValue;
        }

        @Override
        public String getAsText() {
            return sourceCsvValue;
        }

        @Override
        public Object getValue() {
            throw new UnsupportedOperationException("Must be implemented by caller!");
        }
    }
}


