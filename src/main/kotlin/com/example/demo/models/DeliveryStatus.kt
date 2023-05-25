package com.example.demo.models
import com.google.gson.annotations.SerializedName

data class DeliveryStatus(
    @SerializedName("external_delivery_id"       ) var externalDeliveryId       : String?          = null,
    @SerializedName("currency"                   ) var currency                 : String?          = null,
    @SerializedName("delivery_status"            ) var deliveryStatus           : String?          = null,
    @SerializedName("fee"                        ) var fee                      : Int?             = null,
    @SerializedName("pickup_address"             ) var pickupAddress            : String?          = null,
    @SerializedName("pickup_business_name"       ) var pickupBusinessName       : String?          = null,
    @SerializedName("pickup_phone_number"        ) var pickupPhoneNumber        : String?          = null,
    @SerializedName("pickup_instructions"        ) var pickupInstructions       : String?          = null,
    @SerializedName("pickup_external_store_id"   ) var pickupExternalStoreId    : String?          = null,
    @SerializedName("dropoff_address"            ) var dropoffAddress           : String?          = null,
    @SerializedName("dropoff_business_name"      ) var dropoffBusinessName      : String?          = null,
    @SerializedName("dropoff_location"           ) var dropoffLocation          : DropoffLocation? = DropoffLocation(),
    @SerializedName("dropoff_phone_number"       ) var dropoffPhoneNumber       : String?          = null,
    @SerializedName("dropoff_instructions"       ) var dropoffInstructions      : String?          = null,
    @SerializedName("order_value"                ) var orderValue               : Int?             = null,
    @SerializedName("updated_at"                 ) var updatedAt                : String?          = null,
    @SerializedName("pickup_time_estimated"      ) var pickupTimeEstimated      : String?          = null,
    @SerializedName("dropoff_time_estimated"     ) var dropoffTimeEstimated     : String?          = null,
    @SerializedName("tax"                        ) var tax                      : Int?             = null,
    @SerializedName("support_reference"          ) var supportReference         : String?          = null,
    @SerializedName("tracking_url"               ) var trackingUrl              : String?          = null,
    @SerializedName("contactless_dropoff"        ) var contactlessDropoff       : Boolean?         = null,
    @SerializedName("action_if_undeliverable"    ) var actionIfUndeliverable    : String?          = null,
    @SerializedName("tip"                        ) var tip                      : Int?             = null,
    @SerializedName("order_contains"             ) var orderContains            : OrderContains?   = OrderContains(),
    @SerializedName("dropoff_requires_signature" ) var dropoffRequiresSignature : Boolean?         = null,
    @SerializedName("dropoff_cash_on_delivery"   ) var dropoffCashOnDelivery    : Int?             = null
)

data class DropoffLocation (
    @SerializedName("lat" ) var lat : Double? = null,
    @SerializedName("lng" ) var lng : Double? = null
)

data class OrderContains (
    @SerializedName("alcohol" ) var alcohol : Boolean? = null
)