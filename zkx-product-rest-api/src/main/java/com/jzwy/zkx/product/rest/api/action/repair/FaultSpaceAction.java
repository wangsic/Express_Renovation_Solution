package com.jzwy.zkx.product.rest.api.action.repair;

import com.jzwy.zkx.core.assembler.VODTOAssembler;
import com.jzwy.zkx.core.service.contract.PagedResultsResponse;
import com.jzwy.zkx.core.service.contract.Response;
import com.jzwy.zkx.product.rest.api.common.BaseAction;
import com.jzwy.zkx.product.rest.api.vo.FaultSpaceVO;
import com.jzwy.zkx.product.service.repair.FaultSpaceService;
import com.jzwy.zkx.product.service.repair.dto.FaultSpaceDTO;
import com.jzwy.zkx.product.service.repair.query.FaultSpaceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/faultspace")
public class FaultSpaceAction extends BaseAction {

    @Autowired
    private FaultSpaceService faultSpaceService;

    @Autowired
    private VODTOAssembler<FaultSpaceVO, FaultSpaceDTO> faultSpaceVODTOAssembler;

    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.POST}, produces = "application/json; charset=utf-8")
    public Response<Void> add(@RequestBody FaultSpaceVO faultSpace) {
        try {
            FaultSpaceDTO faultSpaceDTO = this.faultSpaceVODTOAssembler.voToDto(faultSpace);
            Response<Void> result = faultSpaceService.add(faultSpaceDTO);

            if (!result.isSuccessful()) {
                return newErrorReturnJson(result.getMessage());
            }
            return newDataReturnJson(result.getData());
        } catch (Exception e) {
            return newErrorReturnJson("添加故障空间操作操作失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/detail", produces = "application/json; charset=utf-8")
    public Response detail(@RequestParam("id") Long id) {
        try {
            Response<FaultSpaceDTO> response = this.faultSpaceService.get(id);
            FaultSpaceVO faultSpaceVO = faultSpaceVODTOAssembler.dtoToVo(response.getData());
            return newDataReturnJson(faultSpaceVO);
        } catch (Exception e) {
            return newErrorReturnJson("查询故障空间详情失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/list", produces = "application/json; charset=utf-8")
    public Response list(HttpServletRequest request) {
        try {
            FaultSpaceQuery query = new FaultSpaceQuery();
            query.setCode(this.getString(request, "code", null));
            query.setName(this.getString(request, "name", null));
            query.setPageIndex(this.getInt(request, "pageIndex", null));
            query.setPageSize(this.getInt(request, "pageSize", null));
            PagedResultsResponse<FaultSpaceDTO> response = faultSpaceService.list(query);
            List<FaultSpaceVO> faultSpaceVOList = faultSpaceVODTOAssembler.dtoListToVoList(response.getData());
            return newListReturnResponse(faultSpaceVOList, response.getPage());
        } catch (Exception e) {
            return newErrorReturnJson("查询故障空间列表失败");
        }
    }
}
