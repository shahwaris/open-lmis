package org.openlmis.rnr.builder;

import com.natpryce.makeiteasy.Instantiator;
import com.natpryce.makeiteasy.Property;
import com.natpryce.makeiteasy.PropertyLookup;
import org.openlmis.rnr.domain.RequisitionGroup;

import static com.natpryce.makeiteasy.Property.newProperty;

public class RequisitionGroupBuilder {
    public static final Property<RequisitionGroup, String> code = newProperty();
    public static final Property<RequisitionGroup, String> name = newProperty();

    public static final String REQUISITION_GROUP_CODE = "RG1";
    public static final String REQUISITION_GROUP_NAME = "RG NAME";

    public static final Instantiator<RequisitionGroup> defaultRequisitionGroup = new Instantiator<RequisitionGroup>() {
        @Override
        public RequisitionGroup instantiate(PropertyLookup<RequisitionGroup> lookup) {
            RequisitionGroup requisitionGroup = new RequisitionGroup();
            requisitionGroup.setCode(lookup.valueOf(code, REQUISITION_GROUP_CODE));
            requisitionGroup.setName(lookup.valueOf(name, REQUISITION_GROUP_NAME));
            requisitionGroup.setModifiedBy("user");
            return requisitionGroup;
        }
    };
}
