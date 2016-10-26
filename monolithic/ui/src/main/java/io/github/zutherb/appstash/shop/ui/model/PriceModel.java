package io.github.zutherb.appstash.shop.ui.model;

import org.apache.wicket.model.IModel;

import java.text.NumberFormat;

/**
 * @author zutherb
 */
public class PriceModel implements IModel<String> {

    private IModel<Number> numberModel;

    public PriceModel(IModel<Number> numberModel) {
        this.numberModel = numberModel;
    }


    @Override
    public String getObject() {
        Number number = numberModel.getObject();
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        return (number != null) ? numberFormat.format(number) : "";
    }

    @Override
    public void setObject(String object) {
        Double value = Double.valueOf(object);
        numberModel.setObject(value);
    }

    @Override
    public void detach() {
        numberModel.detach();
    }
}
