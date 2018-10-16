package com.xinze.haoke.http.config;

/**
 * @author yemao
 *  2017/4/9
 *  网络接口地址!
 */
public interface UrlConfig {
    String LOGIN_TOKEN_URL="获取新token的地址";
    String LOGIN_URL = "transport/app/login";
    String LOGIN_OUT_URL = "transport/app/logout";
    String REGISTER_URL = "transport/app/user/register";
    String GET_VERIFICATION_CODE = "transport/app/user/getVerifyCode";
    String CHECK_VERIFICATION_CODE = "transport/app/user/checkVerifyCode";
    String RESET_PWD = "transport/app/user/resetPwd";
    String GET_BANNER = "transport/app/banner/getBannerListByType";
    String GET_UNREAD_NOTIFY_NUM = "transport/app/notify/getUnReadNotifyNum";
    String GET_FIX_BILL_NUM = "transport/app/bill/getFixBillNum";
    String GET_BILL_ORDER_LIST = "transport/app/billorder/getBillOrderListForOwner";
    String GET_BILL_ORDER_DETAIL = "transport/app/billorder/getBillOrderDetailForOwner";
    String CHANGE_BILL_ORDER_STATUS = "transport/app/billorder/changeBillOrderStatus";
    String GET_BILL_LIST = "transport/app/bill/getBillListForOwner";
    String GET_REGULAR_ROUTE_LIST = "transport/app/regularroute/getRegularRouteList";
    String ADD_REGULAR_ROUTE_LIST = "transport/app/regularroute/addRegularRoute";
    String DEL_REGULAR_ROUTE_LIST = "transport/app/regularroute/delRegularRoute";
    String EDIT_REGULAR_ROUTE_LIST = "transport/app/regularroute/editRegularRoute";
    String SEARCH_ROUTE_LIST = "transport/app/bill/searchRoute";
    String GET_MY_NOTICE = "transport/app/user/myNotice";
    String POST_MY_NOTICE_READ = "transport/app/user/myNotice/read";
    String BACK_BILL = "transport/app/bill/backBill";
    String GET_BILL_DETAIL="transport/app/bill/getBillDetail";
    String GET_CARRY_ORDER_RIGHT = "transport/app/truck/getCarryOrderRight";
    String GET_CARRY_TRUCK_LIST = "transport/app/truck/getCarryTruckList";
    String GET_PROTOCOL_BY_TYPE = "transport/app/protocol/getProtocolByType";
    String CREATE_BILL_ORDER = "transport/app/billorder/createBillOrder";
    String GET_MY_TRUCKOWNER_INVITATION = "transport/app/user/myInvitation/truckOwner";
    String GET_MY_OWNER_INVITATION = "transport/app/user/myInvitation/owner";
    String POST_RESPONSE_INVITATION = "transport/app/user/responseInvitation";
    String GET_MY_TRUCK_DRIVERS = "transport/app/user/myTruckDrivers";
    String GET_MY_COOPERATED_DRIVERS = "transport/app/user/myCooperatedDrivers";
    String POST_DEL_MY_DRIVER = "transport/app/user/truckownerDriver/del";
    String GET_INVITE_DRIVER = "transport/app/user/invite" ;
    String UPLOAD_IMAGE = "transport/app/upload/file" ;
    String DRIVER_CERTIFICATION = "transport/app/user/owner/auth" ;
    String COMPANY_CERTIFICATION = "transport/app/user/owner/auth" ;
    String GET_AREA_LIST = "transport/app/area/getAreaList";

    String GET_MY_TRUCKS = "transport/app/user/myTrucks";
    String GET_AREALIST_BY_PARENT_ID_FOR_SEARCH = "transport/app/area/getAreaListByParentIdForSearch";


    String ABOUT_US = "transport/app/user/aboutus";
    String GET_CUSTOMER_PHONE = "transport/app/customerphone/getCustomerPhone";
    String ADD_TRUCK = "transport/app/user/addTruck";
    String APPOINT_DRIVER_TRUCK = "transport/app/user/appointDriver4Truck";
    String DELETE_MY_TRUCKS = "transport/app/user/delMyTrucks";
    String CHECK_UPDATE = "transport/app/platform/getUpdateVersion";
    String CHANGE_PASS_WORD = "transport/app/user/modifyPWD";
    String GET_COUNT = "transport/app/user/count";
    String GET_MY_USUAL_CARGO = "transport/app/user/myUsualCargo";
    String GET_MY_USUAL_CARGO_ADD = "transport/app/user/myUsualCargo/add";
    String GET_MY_USUAL_CARGO_DEL = "transport/app/user/myUsualCargo/del";
    String GET_MY_INVITE_DRIVER = "transport/app/user/myCooperatedDrivers/record";
    String INVITE_DRIVER = "transport/app/user/invite";
    String RELEASE_THE_BILL_OF_GOODS = "transport/app/bill/deliverGoods";

    String GET_CAR_TYPE = "transport/app/truck/getTruckCodeList";
    String GET_COOPERATED_DRIVERS = "transport/app/user/myCooperatedDrivers";
    String GET_BILL_ORDER_LIST_FOR_OWNER = "transport/app/billorder/getBillOrderListForOwner";
}

