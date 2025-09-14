rootProject.name = "efood"

include("service-registry")
include("ms-orders")
include("api-gateway")

include("ms-payments")
include("ms-users")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")