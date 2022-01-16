package com.example.demo.subtask;

import com.example.demo.TestsUtil;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

class SubtaskEntityTest {
    @Test
    void equalsReturnsTrueForSameReference() {
        var entity = new SubtaskEntity();
        assertTrue(entity.equals(entity));  // do not use assertEquals: the equality comparison is made by junit itself
    }

    @Test
    void equalsReturnsFalseIfOtherIsNull() {
        assertFalse(new SubtaskEntity().equals(null));
    }

    @Test
    void equalsReturnsFalseIfTheUnderlyingClassesOfTheProxiedPersistenceClassesReportedByHibernateAreNotEqual() {
        var entity1 = TestsUtil.subtaskEntityStub();
        var entity2 = TestsUtil.subtaskEntityStub();
        try (var mockedHibernate = mockStatic(Hibernate.class)) {
            mockedHibernate.when(() -> Hibernate.getClass(any(SubtaskEntity.class)))
                           .thenReturn(String.class)
                           .thenReturn(Boolean.class);
            assertFalse(entity1.equals(entity2));
        }
    }

    @Test
    void equalsReturnsTrueForEntitiesWithEqualsId() {
        var entity1 = TestsUtil.subtaskEntityStub();
        var entity2 = TestsUtil.subtaskEntityStub();
        entity2.setId(entity1.getId());

        assertTrue(entity1.equals(entity2));
    }

    @Test
    void equalsReturnsFalseForEntitiesWithDifferentId() {
        var entity1 = TestsUtil.subtaskEntityStub();
        var entity2 = TestsUtil.subtaskEntityStub();
        entity2.setId(entity1.getId() + 1);

        assertFalse(entity1.equals(entity2));
    }

    @Test
    void hashCodeIsClassHasCode() {
        assertEquals(SubtaskEntity.class.hashCode(), new SubtaskEntity().hashCode());
    }

    @Test
    void toStringShowsEntityId() {
        assertTrue(new SubtaskEntity().toString().contains("id="));
    }
}