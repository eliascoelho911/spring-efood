-- Create orders database
CREATE DATABASE orders_db;

-- Create payments database  
CREATE DATABASE payments_db;

-- Grant privileges to efood_user for both databases
GRANT ALL PRIVILEGES ON DATABASE orders_db TO efood_user;
GRANT ALL PRIVILEGES ON DATABASE payments_db TO efood_user;