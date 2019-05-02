//
// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See LICENSE.md file in the project root for full license information.
//
// handle_helpers.h: Helper methods for SPX*HANDLE related capabilities
//

#pragma once
#include <speechapi_c_common.h>
#include "try_catch_helpers.h"


namespace Microsoft {
namespace CognitiveServices {
namespace Speech {
namespace Impl {


template<class handle_type, class ptr_type>
bool Handle_IsValid(handle_type handle)
{
    SPXAPI_INIT_HR_TRY(hr)
    {
        auto handletable = CSpxSharedPtrHandleTableManager::Get<ptr_type, handle_type>();
        return handletable->IsTracked(handle);
    }
    SPXAPI_CATCH_AND_RETURN(hr, false);
}


template<class handle_type, class ptr_type>
SPXHR Handle_Close(handle_type handle)
{
    SPX_RETURN_HR_IF(SPXERR_INVALID_ARG, handle == nullptr);
    SPXAPI_INIT_HR_TRY(hr)
    {
        auto handletable = CSpxSharedPtrHandleTableManager::Get<ptr_type, handle_type>();
        handletable->StopTracking(handle);
    }
    SPXAPI_CATCH_AND_RETURN_HR(hr);
}


} } } } // Microsoft::CognitiveServices::Speech::Impl