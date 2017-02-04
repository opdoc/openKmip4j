# openKmip4j
This project  aims to provide an  open-source and  fail-safely  usable solution
for using KMIP both on client and server sides.

The usage  of this  implementation  does not  require deeper  knowledge  of the
protocol itself as  the API guides the user.  Also this  implementation aims to
handle secrets (key  material and passwords) in a tamperproof  way implementing
org.identityconnectors.common.security's GuardedString and GuardedByteArray.
