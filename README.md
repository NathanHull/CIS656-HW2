# CIS656-HW2-RMI
Java Remote Method Invocation Chat App (and Tutorial)

## Design
A central PresenceService will run on the server, and clients will register via Java RMI methods (found in RegistrationInfo.java). In this case, clients will query the server for other clients' information by the ResgistrationInfo methods, which includes their address/port.
