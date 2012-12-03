package org.openlmis.rnr.service;

import lombok.NoArgsConstructor;
import org.openlmis.core.domain.Product;
import org.openlmis.core.service.ProductService;
import org.openlmis.rnr.domain.Rnr;
import org.openlmis.rnr.domain.RnrLineItem;
import org.openlmis.rnr.domain.RnrStatus;
import org.openlmis.rnr.repository.RnrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@NoArgsConstructor
public class RnrService {

    private RnrRepository rnrRepository;

    private ProductService productService;

    private RnrTemplateService rnrTemplateService;

    @Autowired
    public RnrService(RnrRepository rnrRepository, ProductService productService, RnrTemplateService rnrTemplateService) {
        this.rnrRepository = rnrRepository;
        this.productService = productService;
        this.rnrTemplateService = rnrTemplateService;
    }

    @Transactional
    public Rnr initRnr(String facilityCode, String programCode, String modifiedBy) {
        Rnr requisition = new Rnr(facilityCode, programCode, RnrStatus.INITIATED, modifiedBy);
        List<Product> products = productService.getByFacilityAndProgram(facilityCode, programCode);
        for (Product product : products) {
            RnrLineItem requisitionLineItem = new RnrLineItem(requisition.getId(), product, modifiedBy);
            requisition.add(requisitionLineItem);
        }
        rnrRepository.insert(requisition);
        return requisition;
    }

    public void save(Rnr rnr) {
        rnrRepository.update(rnr);
    }
}
