/*
 * Copyright © 2013 VillageReach.  All Rights Reserved.  This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0.
 *
 *  If a copy of the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.openlmis.core.repository;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openlmis.core.domain.RegimenColumn;
import org.openlmis.core.domain.RegimenTemplate;
import org.openlmis.core.repository.mapper.RegimenColumnMapper;
import org.openlmis.db.categories.UnitTests;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Category(UnitTests.class)
@RunWith(MockitoJUnitRunner.class)
public class RegimenColumnRepositoryTest {

  @InjectMocks
  RegimenColumnRepository repository;

  @Mock
  RegimenColumnMapper mapper;

  @Test
  public void shouldGetMasterRegimenColumns() throws Exception {
    List<RegimenColumn> regimenColumns = new ArrayList<>();
    when(mapper.getMasterRegimenColumns()).thenReturn(regimenColumns);

    List<RegimenColumn> regimenColumnsFromDb = repository.getMasterRegimenColumnsByProgramId();

    verify(mapper).getMasterRegimenColumns();
    assertThat(regimenColumnsFromDb, is(regimenColumns));
  }

  @Test
  public void shouldInsertRegimenColumn() throws Exception {
    Long userId = 5L;
    List<RegimenColumn> regimenColumns = new ArrayList<>();
    RegimenColumn regimenColumn = new RegimenColumn();
    regimenColumns.add(regimenColumn);

    RegimenTemplate regimenTemplate = new RegimenTemplate(1l, regimenColumns);

    repository.save(regimenTemplate, userId);

    verify(mapper).insert(regimenColumn, regimenTemplate.getProgramId());
    assertThat(regimenColumn.getModifiedBy(), is(userId));
    assertThat(regimenColumn.getCreatedBy(), is(userId));
  }

  @Test
  public void shouldUpdateRegimenColumnIfIdExists() throws Exception {
    Long userId = 5L;
    List<RegimenColumn> regimenColumns = new ArrayList<>();
    RegimenColumn regimenColumn = new RegimenColumn();
    regimenColumn.setId(2L);
    regimenColumns.add(regimenColumn);

    RegimenTemplate regimenTemplate = new RegimenTemplate(1l, regimenColumns);

    repository.save(regimenTemplate, userId);

    verify(mapper).update(regimenColumn);
    assertThat(regimenColumn.getModifiedBy(), is(userId));
  }
}
