package org.openlmis.rnr.repository.mapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openlmis.core.builder.FacilityBuilder;
import org.openlmis.core.domain.Facility;
import org.openlmis.core.repository.mapper.FacilityMapper;
import org.openlmis.core.repository.mapper.ProgramSupportedMapper;
import org.openlmis.rnr.domain.Rnr;
import org.openlmis.rnr.domain.RnrStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.natpryce.makeiteasy.MakeItEasy.a;
import static com.natpryce.makeiteasy.MakeItEasy.make;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.openlmis.core.builder.FacilityBuilder.FACILITY_CODE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:applicationContext-requisition.xml")
public class RnrMapperIT {

    @Autowired
    private FacilityMapper facilityMapper;
    @Autowired
    private ProgramSupportedMapper programSupportedMapper;

    @Autowired
    private RnrMapper rnrMapper;

    @Before
    public void setUp() {
        programSupportedMapper.deleteAll();
        rnrMapper.deleteAll();
        facilityMapper.deleteAll();
        Facility facility = make(a(FacilityBuilder.defaultFacility));
        facilityMapper.insert(facility);
    }

    @Test
    public void shouldReturnRequisitionId() {
        Rnr requisition = new Rnr(FACILITY_CODE, "HIV", RnrStatus.INITIATED, "user");
        int id1 = rnrMapper.insert(requisition);
        int id2 = rnrMapper.insert(new Rnr(FACILITY_CODE, "ARV", RnrStatus.INITIATED, "user"));
        assertThat(id1, is(id2 - 1));
    }

    @Test
    public void shouldReturnRequisitionById() {
        Rnr requisition = new Rnr(FACILITY_CODE, "HIV", RnrStatus.INITIATED, "user");
        int id = rnrMapper.insert(requisition);
        Rnr requisitionById = rnrMapper.getRequisitionById(id);
        assertThat(requisitionById.getId(), is(id));
        assertThat(requisitionById.getProgramCode(), is(equalTo("HIV")));
        assertThat(requisitionById.getFacilityCode(), is(equalTo(FACILITY_CODE)));
        assertThat(requisitionById.getModifiedBy(), is(equalTo("user")));
        assertThat(requisitionById.getStatus(), is(equalTo(RnrStatus.INITIATED)));
    }

    @Test
    public void shouldUpdateRequisition() {
        Rnr requisition = new Rnr(FACILITY_CODE, "HIV", RnrStatus.INITIATED, "user");
        int id = rnrMapper.insert(requisition);
        requisition.setId(id);
        requisition.setModifiedBy("user1");
        requisition.setStatus(RnrStatus.CREATED);

        rnrMapper.update(requisition);

        Rnr updatedRequisition = rnrMapper.getRequisitionById(id);

        assertThat(updatedRequisition.getId(), is(id));
        assertThat(updatedRequisition.getModifiedBy(), is(equalTo("user1")));
        assertThat(updatedRequisition.getStatus(), is(equalTo(RnrStatus.CREATED)));
    }

    @After
    public void tearDown() throws Exception {
        programSupportedMapper.deleteAll();
        rnrMapper.deleteAll();
        facilityMapper.deleteAll();
    }

}
