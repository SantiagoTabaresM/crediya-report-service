package co.com.pragma.dynamodb.helper;

import co.com.pragma.dynamodb.DynamoDBTemplateAdapter;
import co.com.pragma.dynamodb.ApprovedLoansEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import reactor.test.StepVerifier;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class TemplateAdapterOperationsTest {

    @Mock
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private DynamoDbAsyncTable<ApprovedLoansEntity> customerTable;

    private ApprovedLoansEntity approvedLoansEntity;
/*
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(dynamoDbEnhancedAsyncClient.table("table_name", TableSchema.fromBean(ApprovedLoansEntity.class)))
                .thenReturn(customerTable);

        approvedLoansEntity = new ApprovedLoansEntity();
        approvedLoansEntity.setId("id");
        approvedLoansEntity.setAtr1("atr1");
    }

    @Test
    void modelEntityPropertiesMustNotBeNull() {
        ApprovedLoansEntity approvedLoansEntityUnderTest = new ApprovedLoansEntity("id", "atr1");

        assertNotNull(approvedLoansEntityUnderTest.getId());
        assertNotNull(approvedLoansEntityUnderTest.getAtr1());
    }

    @Test
    void testSave() {
        when(customerTable.putItem(approvedLoansEntity)).thenReturn(CompletableFuture.runAsync(()->{}));
        when(mapper.map(approvedLoansEntity, ApprovedLoansEntity.class)).thenReturn(approvedLoansEntity);

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBTemplateAdapter.save(approvedLoansEntity))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void testGetById() {
        String id = "id";

        when(customerTable.getItem(
                Key.builder().partitionValue(AttributeValue.builder().s(id).build()).build()))
                .thenReturn(CompletableFuture.completedFuture(approvedLoansEntity));
        when(mapper.map(approvedLoansEntity, Object.class)).thenReturn("value");

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBTemplateAdapter.getById("id"))
                .expectNext("value")
                .verifyComplete();
    }

    @Test
    void testDelete() {
        when(mapper.map(approvedLoansEntity, ApprovedLoansEntity.class)).thenReturn(approvedLoansEntity);
        when(mapper.map(approvedLoansEntity, Object.class)).thenReturn("value");

        when(customerTable.deleteItem(approvedLoansEntity))
                .thenReturn(CompletableFuture.completedFuture(approvedLoansEntity));

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        StepVerifier.create(dynamoDBTemplateAdapter.delete(approvedLoansEntity))
                .expectNext("value")
                .verifyComplete();
    }*/
}