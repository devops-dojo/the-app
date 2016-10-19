package io.github.zutherb.appstash.shop.ui.panel;

import io.github.zutherb.appstash.shop.service.order.model.OrderInfo;
import io.github.zutherb.appstash.shop.ui.panel.base.AbstractShopBasePanel;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 * @author zutherb
 */
public class DeliveryAdressEditPanel extends AbstractShopBasePanel {
    private static final long serialVersionUID = -7031284847456677558L;

    private IModel<OrderInfo> orderModel;

    public DeliveryAdressEditPanel(String id, IModel<OrderInfo> orderModel) {
        super(id);
        this.orderModel = orderModel;
        add(deliveryAdressEditForm());
    }

    private Component deliveryAdressEditForm() {
        Form<OrderInfo> deliveryAdressEditForm = new Form<>("deliveryAdressEditForm");
        deliveryAdressEditForm.add(new TextField<>("firstname", new PropertyModel<>(orderModel, "deliveryAddress.firstname")).setRequired(true));
        deliveryAdressEditForm.add(new TextField<>("lastname", new PropertyModel<>(orderModel, "deliveryAddress.lastname")).setRequired(true));
        deliveryAdressEditForm.add(new TextField<>("street", new PropertyModel<>(orderModel, "deliveryAddress.street")).setRequired(true));
        deliveryAdressEditForm.add(new TextField<>("zip", new PropertyModel<>(orderModel, "deliveryAddress.zip")).setRequired(true));
        deliveryAdressEditForm.add(new TextField<>("city", new PropertyModel<>(orderModel, "deliveryAddress.city")).setRequired(true));
        deliveryAdressEditForm.add(new SubmitLink("deliveryAdressEditFormSubmit") {
            private static final long serialVersionUID = 8821619700889289116L;

            @Override
            public void onSubmit() {
                deliveryAdressEditFormSubmit();
            }
        });
        return deliveryAdressEditForm;
    }

    protected void deliveryAdressEditFormSubmit() {

    }

    @Override
    protected void onBeforeRender() {
        setVisible(isAuthorized());
        super.onBeforeRender();
    }

}
