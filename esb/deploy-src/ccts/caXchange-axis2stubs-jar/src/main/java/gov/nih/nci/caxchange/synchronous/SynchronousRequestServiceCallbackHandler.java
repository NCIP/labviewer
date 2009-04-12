
/**
 * SynchronousRequestServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

    package gov.nih.nci.caxchange.synchronous;

    /**
     *  SynchronousRequestServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class SynchronousRequestServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public SynchronousRequestServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public SynchronousRequestServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for processRequestSynchronously method
            * override this method for handling normal response from processRequestSynchronously operation
            */
           public void receiveResultprocessRequestSynchronously(
                    gov.nih.nci.caxchange.synchronous.SynchronousRequestServiceStub.CaXchangeResponseMessage result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from processRequestSynchronously operation
           */
            public void receiveErrorprocessRequestSynchronously(java.lang.Exception e) {
            }
                


    }
    