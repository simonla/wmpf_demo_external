@file:Suppress("SpellCheckingInspection")

package com.tencent.wmpf.demo

import com.tencent.mm.ipcinvoker.IPCInvokeCallbackEx
import com.tencent.wmpf.cli.task.*
import com.tencent.wmpf.cli.task.pb.WMPFBaseRequestHelper
import com.tencent.wmpf.cli.task.pb.WMPFIPCInvoker
import com.tencent.wmpf.proto.*
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.Exception

object Api {

    fun activateDevice(productId: Int, keyVerion: Int,
                       deviceId: String, signature: String, hostAppId: String): Single<WMPFActivateDeviceResponse> {
        return Single.create {
            val request = WMPFActivateDeviceRequest().apply {
                this.baseRequest = WMPFBaseRequestHelper.checked()
                this.productId = productId
                this.keyVersion = keyVerion
                this.deviceId = deviceId
                this.signature = signature
                this.hostAppId = hostAppId
            }

            val result = WMPFIPCInvoker.invokeAsync<IPCInvokerTask_ActivateDevice, WMPFActivateDeviceRequest, WMPFActivateDeviceResponse>(
                    request,
                    IPCInvokerTask_ActivateDevice::class.java,
                    object : IPCInvokeCallbackEx<WMPFActivateDeviceResponse> {
                        override fun onBridgeNotFound() {
                            it.onError(Exception("bridge not found"))
                        }

                        override fun onCallback(response: WMPFActivateDeviceResponse) {
                            it.onSuccess(response)
                        }
                    })

            if (!result) {
                it.onError(Exception("invoke activateDevice fail"))
            }
        }
    }

    fun preloadRuntime(): Single<WMPFPreloadRuntimeResponse> {
        return Single.create {
            val request = WMPFPreloadRuntimeRequest().apply {
                baseRequest = WMPFBaseRequestHelper.checked()
            }

            val result = WMPFIPCInvoker.invokeAsync<IPCInvokerTask_PreloadRuntime,
                    WMPFPreloadRuntimeRequest, WMPFPreloadRuntimeResponse>(
                    request,
                    IPCInvokerTask_PreloadRuntime::class.java
            ) { response -> it.onSuccess(response) }

            if (!result) {
                it.onError(Exception("invoke authorize fail"))
            }
        }
    }

    fun authorize(appId: String, ticket: String, scope: String): Single<WMPFAuthorizeResponse> {
        return Single.create {
            val request = WMPFAuthorizeRequest()
            request.baseRequest = WMPFBaseRequestHelper.checked()
            request.ticket = ticket
            request.appId = appId // OpenSDK AppId for App
            request.scope = scope

            val result = WMPFIPCInvoker.invokeAsync<IPCInvokerTask_Authorize,
                    WMPFAuthorizeRequest, WMPFAuthorizeResponse>(
                    request,
                    IPCInvokerTask_Authorize::class.java
            ) { response -> it.onSuccess(response) }

            if (!result) {
                it.onError(Exception("invoke authorize fail"))
            }
        }
    }

    fun authorizeNoLogin(appId: String, ticket: String, scope: String): Single<WMPFAuthorizeNoLoginResponse> {
        return Single.create {
            val request = WMPFAuthorizeNoLoginRequest()
            request.baseRequest = WMPFBaseRequestHelper.checked()
            request.deviceInfo = ""
            request.deviceTicket = ""
            request.ticket = ticket
            request.appId = appId // OpenSDK AppId for App
            request.scope = scope

            val result = WMPFIPCInvoker.invokeAsync<IPCInvokerTask_AuthorizeNoLogin,
                    WMPFAuthorizeNoLoginRequest, WMPFAuthorizeNoLoginResponse>(
                    request,
                    IPCInvokerTask_AuthorizeNoLogin::class.java
            ) { response -> it.onSuccess(response) }

            if (!result) {
                it.onError(Exception("invoke authorize fail"))
            }
        }
    }

    fun launchWxaApp(launchAppId: String, path: String, appType: Int = 0): Single<WMPFLaunchWxaAppResponse> {
        return Single.create {
            val request = WMPFLaunchWxaAppRequest()
            request.baseRequest = WMPFBaseRequestHelper.checked()
            // Launch target(wxa appId)
            // WARNING: hostAppIds and wxaAppIds are binded sets.
            request.appId = launchAppId // Binded with HOST_APPID: wx64b7714cf1f64585
            request.path = path
            request.appType = appType // 0-正式版 1-开发版 2-体验版

            val result = WMPFIPCInvoker.invokeAsync<IPCInvokerTask_LaunchWxaApp, WMPFLaunchWxaAppRequest,
                    WMPFLaunchWxaAppResponse>(
                    request,
                    IPCInvokerTask_LaunchWxaApp::class.java
            ) { response -> it.onSuccess(response) }

            if (!result) {
                it.onError(Exception("invoke launchWxaApp fail"))
            }
        }
    }

    fun launchWxaAppByScan(rawData: String): Single<WMPFLaunchWxaAppByQRCodeResponse> {
        return Single.create {
            val request = WMPFLaunchWxaAppByQRCodeRequest()
            request.baseRequest = WMPFBaseRequestHelper.checked()
            request.baseRequest.clientApplicationId = ""
            request.rawData = rawData // rawData from qrcode

            val result = WMPFIPCInvoker.invokeAsync<IPCInvokerTask_LaunchWxaAppByQrCode,
                    WMPFLaunchWxaAppByQRCodeRequest, WMPFLaunchWxaAppByQRCodeResponse>(
                        request,
                        IPCInvokerTask_LaunchWxaAppByQrCode::class.java
                ) { response -> it.onSuccess(response) }

            if (!result) {
                it.onError(Exception("invoke launchWxaAppByScan fail"))
            }
        }
    }

    fun closeWxaApp(appId: String): Single<WMPFCloseWxaAppResponse> {
        return Single.create {
            val request = WMPFCloseWxaAppRequest()
            request.baseRequest = WMPFBaseRequestHelper.checked()
            request.baseRequest.clientApplicationId = ""
            request.appId = appId

            val result = WMPFIPCInvoker.invokeAsync<IPCInvokerTask_CloseWxaApp,
                    WMPFCloseWxaAppRequest, WMPFCloseWxaAppResponse>(
                    request,
                    IPCInvokerTask_CloseWxaApp::class.java
            ) { response -> it.onSuccess(response) }

            if (!result) {
                it.onError(Exception("invoke launchWxaAppByScan fail"))
            }
        }
    }

    fun manageBackgroundMusic(showManageUI: Boolean = true, forceRequestFullscreen: Boolean = false): Single<WMPFManageBackgroundMusicResponse> {
        return Single.create {
            val request = WMPFManageBackgroundMusicRequest()
            request.baseRequest = WMPFBaseRequestHelper.checked()
            request.showManageUI = showManageUI
            request.forceRequestFullscreen = forceRequestFullscreen
            val result = WMPFIPCInvoker.invokeAsync<IPCInvokerTask_ManageBackgroundMusic, WMPFManageBackgroundMusicRequest,
                    WMPFManageBackgroundMusicResponse>(
                    request,
                    IPCInvokerTask_ManageBackgroundMusic::class.java
            ) { response -> it.onSuccess(response) }

            if (!result) {
                it.onError(Exception("invoke manageBackgroundMusic fail"))
            }
        }
    }

    // 监听背景音频改变
    fun notifyBackgroundMusic(): Observable<WMPFNotifyBackgroundMusicResponse> {
        return Observable.create {
            val request = WMPFNotifyBackgroundMusicRequest()
            request.baseRequest = WMPFBaseRequestHelper.checked()
            request.notify = true
            val result = WMPFIPCInvoker.invokeAsync<IPCInvokerTask_NotifyBackgroundMusic,
                    WMPFNotifyBackgroundMusicRequest, WMPFNotifyBackgroundMusicResponse>(
                    request,
                    IPCInvokerTask_NotifyBackgroundMusic::class.java
            ) {
                response ->
                /**
                 * {@see com.tencent.wmpf.cli.task.IPCInvokerTask_NotifyBackgroundMusic}
                 * val START = 1
                 * val RESUME = 2
                 * val PAUSE = 3
                 * val STOP = 4
                 * val COMPLETE = 5
                 * val ERROR = 6
                 **/
                it.onNext(response)
            }

            if (!result) {
                it.onError(Exception("invoke notifyBackgroundMusic fail"))
            }
        }
    }

    fun deauthorize(): Single<WMPFDeauthorizeResponse> {
        return Single.create {
            val request = WMPFDeauthorizeRequest()
            request.baseRequest = WMPFBaseRequestHelper.checked()

            val result = WMPFIPCInvoker.invokeAsync<IPCInvokerTask_Deauthorize,
                    WMPFDeauthorizeRequest, WMPFDeauthorizeResponse>(
                    request,
                    IPCInvokerTask_Deauthorize::class.java
            ) { response -> it.onSuccess(response) }

            if (!result) {
                it.onError(Exception("invoke authorize fail"))
            }
        }
    }

    fun listeningPushMsg(): Observable<WMPFPushMsgResponse> {
        return Observable.create {
            val request = WMPFPushMsgRequest()
            request.baseRequest = WMPFBaseRequestHelper.checked()
            val result = WMPFIPCInvoker.invokeAsync<IPCInovkerTask_SetPushMsgCallback,
                    WMPFPushMsgRequest, WMPFPushMsgResponse>(request, IPCInovkerTask_SetPushMsgCallback::class.java) { response ->
                it.onNext(response)
            }
            if (!result) {
                it.onError(Exception("invoke authorize fail"))
            }
        }
    }

}