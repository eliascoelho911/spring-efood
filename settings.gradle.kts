rootProject.name = "efood"

include("service-registry")
include("ms-orders")
include("api-gateway")

include("ms-payments")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")