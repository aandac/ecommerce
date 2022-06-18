package com.ecommerce.service.customer;

import com.ecommerce.controller.customer.model.CustomerCreateRequest;
import com.ecommerce.controller.customer.model.CustomerDetailResponse;
import com.ecommerce.controller.customer.model.CustomerDetailUpdateRequest;
import com.ecommerce.dao.entity.User;

public interface CustomerService {

    void createCustomer(CustomerCreateRequest request);

    void checkOneClickData(User user);

    CustomerDetailResponse getCustomerDetail(User user);

    void updateCustomerDetail(User user, CustomerDetailUpdateRequest request);
}
