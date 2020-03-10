//Author: Phung Khanh Chi

package com.example.ad_team10.clients;

import com.example.ad_team10.models.CollectionPoint;
import com.example.ad_team10.models.CustomDepartment;
import com.example.ad_team10.models.CustomDeptEmployee;
import com.example.ad_team10.models.CustomDisbursementList;
import com.example.ad_team10.models.CustomMembershipUser;
import com.example.ad_team10.models.CustomPurchaseOrder;
import com.example.ad_team10.models.CustomItem;
import com.example.ad_team10.models.CustomRequisition;
import com.example.ad_team10.models.CustomRetrievalList;
import com.example.ad_team10.models.CustomRetrievalListDetail;
import com.example.ad_team10.viewModels.LoginView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StationeryService {
    @POST("Mobile/Login")
    Call<CustomMembershipUser> isValidUser(@Body LoginView loginView);

    @GET("Mobile/GetPurchaseOrders")
    Call<List<CustomPurchaseOrder>> getPurchaseOrder();

    @GET("Mobile/GetPurchaseOrderDetail/{id}")
    Call<CustomPurchaseOrder> getPurchaseOrderDetail(@Path("id") int id);

    @PUT("Mobile/UpdatePurchaseOrder")
    Call<Void> updatePurchaseOrder(@Body CustomPurchaseOrder purchaseOrder);

    @GET("Mobile/GetRetrievalList")
    Call<List<CustomItem>> getRetrievalList();

    @GET("Mobile/GetRetrievalListByItem/{id}")
    Call<List<CustomRetrievalListDetail>> getRetrievalListByItem(@Path("id") int itemId);

    @PUT("Mobile/UpdateRetrievalListByItem")
    Call<Void> updateRetrievalListByItem(@Body CustomRetrievalList customRetrievalList);

    @GET("Mobile/GetDepartmentList")
    Call<List<CustomDepartment>> getDepartmentList();

    @GET("Mobile/GetDisbursementList/{id}")
    Call<List<CustomItem>> getDisbursementList(@Path("id") int departmentId);

    @PUT("Mobile/UpdateDisbursementList")
    Call<Void> updateDisbursementList(@Body CustomDisbursementList disbursementList);

    @GET("Mobile/GetDepartmentIdFromUserId/{id}")
    Call<Integer> getDepartmentIdFromUserId(@Path("id") int deptEmployeeId);

    @GET("Mobile/GetPendingRequisitions/{id}")
    Call<List<CustomRequisition>> getPendingRequisition(@Path("id") int departmentId);

    @GET("Mobile/GetRequisitionDetail/{id}")
    Call<CustomRequisition> getRequisitionDetail(@Path("id") int requisitionId);

    @PUT("Mobile/ApproveRequisition/{id}")
    Call<Void> approveRequisition(@Path("id") int requisitionId);

    @PUT("Mobile/RejectRequisition/{id}")
    Call<Void> rejectRequisition(@Path("id") int requisitionId);

    @GET("Mobile/GetCurrentRep/{id}")
    Call<CustomDeptEmployee> getCurrentRep(@Path("id") int departmentId);

    @GET("Mobile/GetEmployeeList/{id}")
    Call<List<CustomDeptEmployee>> getEmployeeList(@Path("id") int departmentId);

    @PUT("Mobile/AssignRepresentative")
    Call<Void> assignRepresentative(@Body CustomDeptEmployee customDeptEmployee);

    @GET("Mobile/GetDisbursementDetailByItem/{departmentId}/{itemId}")
    Call<List<CustomRequisition>> getDisbursementDetailByItem(@Path("departmentId") int deptId, @Path("itemId") int itemId);

    @PUT("Mobile/UpdateDisbursementDetailByItem")
    Call<Void> updateDisbursementDetailByItem(@Body CustomRequisition[] requisitions);

    @GET("Mobile/GetCollectionPoint/{id}")
    Call<CollectionPoint> getCollectionPoint(@Path("id") int departmentId);

    @GET("Mobile/GetCollectionPointList/{id}")
    Call<List<CollectionPoint>> getCollectionPointList(@Path("id") int departmentId);

    @PUT("Mobile/ChangeCollectionPoint")
    Call<Void> changeCollectionPoint(@Body CustomDepartment customDepartment);
//    //i.e. http://localhost/api/institute/Students/1
//    //Get student record base on ID
//    @GET("/Requisition/{id}")
//    public void getRequisitionById(@Path("id") Integer id, Callback<Requisition> callback);
//
//    //i.e. http://localhost/api/institute/Students/1
//    //Delete student record base on ID
//    @DELETE("/Students/{id}")
//    public void deleteRequisitionById(@Path("id") Integer id,Callback<Requisition> callback);
//
//    //i.e. http://localhost/api/institute/Students/1
//    //PUT student record and post content in HTTP request BODY
//    @PUT("/Students/{id}")
//    public void updateRequisitionById(@Path("id") Integer id, @Body Requisition custom_requisition,
//                                      Callback<Requisition> callback);

    //i.e. http://localhost/api/institute/Students
    //Add student record and post content in HTTP request BODY
//    @POST("/institute/Students")
//    public void addStudent(@Body Student student,Callback<Student> callback);
}
