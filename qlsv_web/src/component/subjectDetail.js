import { useParams } from "react-router-dom";
import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import axios from "axios";

const baseURLGet = (mamh) => {
  return `http://localhost:8901/subjects/${mamh}`;
};

export const SubjectDetail = () => {
  let { id } = useParams();

  const [grades, setGrades] = useState([]);
  const [subjectName, setSubjectName] = useState([]);

  const [selectedId, setSelectedId] = useState();
  const columns = [
    { field: "MASV", headerName: "Mã sinh viên", width: 150 },
    { field: "TENSV", headerName: "Tên sinh viên", width: 150 },
    { field: "DIEMCC", headerName: "Điểm chuyên cần", width: 150 },
    { field: "DIEMBTL", headerName: "Điểm bài tập lớn", width: 150 },
    { field: "DIEMCK", headerName: "Điểm cuối kì", width: 150 },
  ];

  useEffect(() => {
    axios.get(baseURLGet(id)).then((response) => {
      const datas = response.data.studentGrades;
      if(datas.length > 0){
        setSubjectName(datas[0].TENMH)
      }
      const res = datas.map((x) => {
        return { ...x, id: datas.indexOf(x) };
      });
      setGrades(res);
    });
  }, [id]);

  const handleRowClick = (e) => {
    setSelectedId(e.row.id);
  };

  if (!grades) return <div>Loading...</div>;

  return (
    <div>
      <div style={{ margin: 30 }}>
        <h2>List grade of subject {id} - {subjectName}</h2>
      </div>
      <div style={{ display: "flex" }}>
        <div style={{ width: 900, margin: 30 }}>
          <DataGrid
            rows={grades}
            columns={columns}
            initialState={{
              pagination: {
                paginationModel: { page: 0, pageSize: 7 },
              },
            }}
            onRowClick={handleRowClick}
          />
        </div>
      </div>
    </div>
  );
};
