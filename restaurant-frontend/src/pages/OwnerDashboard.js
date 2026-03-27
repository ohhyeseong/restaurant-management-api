import { useState, useEffect } from 'react';
import { Container, Card, Form, Button, Row, Col, Table, Modal } from 'react-bootstrap';
import api from '../api/axiosConfig';

export default function OwnerDashboard() {
    const [storeForm, setStoreForm] = useState({ name: '', address: '', openTime: '', closeTime: '' });
    const [stores, setStores] = useState([]);

    const [showEditModal, setShowEditModal] = useState(false);
    const [editForm, setEditForm] = useState({ id: null, name: '', address: '', openTime: '', closeTime: '' });

    useEffect(() => {
        fetchStores();
    }, []);

    const fetchStores = async () => {
        try {
            const res = await api.get('/stores');
            setStores(res.data.data);
        } catch (error) {
            console.error('매장 목록을 불러오는 데 실패했습니다.', error);
        }
    };

    const handleChange = (e) => setStoreForm({ ...storeForm, [e.target.name]: e.target.value });
    const handleEditChange = (e) => setEditForm({ ...editForm, [e.target.name]: e.target.value });

    const handleCreateStore = async (e) => {
        e.preventDefault();
        try {
            await api.post('/stores/create', storeForm);
            alert('매장 생성이 완료되었습니다!');
            setStoreForm({ name: '', address: '', openTime: '', closeTime: '' });
            fetchStores();
        } catch (error) {
            alert('매장 생성에 실패했습니다.');
        }
    };

    const handleDelete = async (storeId) => {
        if (window.confirm('정말 이 매장을 삭제하시겠습니까?')) {
            try {
                await api.delete(`/stores/${storeId}`);
                alert('삭제되었습니다.');
                fetchStores();
            } catch (error) {
                alert('삭제에 실패했습니다.');
            }
        }
    };

    const handleEditShow = (store) => {
        setEditForm({
            id: store.id,
            name: store.name,
            address: store.address,
            openTime: store.openTime,
            closeTime: store.closeTime
        });
        setShowEditModal(true);
    };

    const handleUpdateStore = async (e) => {
        e.preventDefault();
        try {
            await api.put(`/stores/${editForm.id}`, {
                name: editForm.name,
                address: editForm.address,
                openTime: editForm.openTime,
                closeTime: editForm.closeTime
            });
            alert('수정되었습니다.');
            setShowEditModal(false);
            fetchStores();
        } catch (error) {
            alert('수정에 실패했습니다.');
        }
    };

    return (
        <Container className="pt-5 mt-5">
            <h2 className="mb-4 text-center">점주 대시보드</h2>

            <Row className="justify-content-md-center mb-5">
                <Col md={8}>
                    <Card className="shadow-sm">
                        <Card.Header as="h5" className="bg-primary text-white">신규 매장 등록</Card.Header>
                        <Card.Body>
                            <Form onSubmit={handleCreateStore}>
                                <Form.Group className="mb-3">
                                    <Form.Label>매장명</Form.Label>
                                    <Form.Control type="text" name="name" value={storeForm.name} onChange={handleChange} required />
                                </Form.Group>
                                <Form.Group className="mb-3">
                                    <Form.Label>매장 주소</Form.Label>
                                    <Form.Control type="text" name="address" value={storeForm.address} onChange={handleChange} required />
                                </Form.Group>
                                <Row className="mb-4">
                                    <Col>
                                        <Form.Group>
                                            <Form.Label>오픈 시간</Form.Label>
                                            <Form.Control type="time" name="openTime" value={storeForm.openTime} onChange={handleChange} required />
                                        </Form.Group>
                                    </Col>
                                    <Col>
                                        <Form.Group>
                                            <Form.Label>마감 시간</Form.Label>
                                            <Form.Control type="time" name="closeTime" value={storeForm.closeTime} onChange={handleChange} required />
                                        </Form.Group>
                                    </Col>
                                </Row>
                                <div className="d-grid">
                                    <Button variant="primary" type="submit">매장 등록하기</Button>
                                </div>
                            </Form>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            <Row className="justify-content-md-center">
                <Col md={10}>
                    <Card className="shadow-sm">
                        <Card.Header as="h5" className="bg-success text-white">내 매장 목록</Card.Header>
                        <Card.Body>
                            <Table striped bordered hover responsive>
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>매장명</th>
                                        <th>주소</th>
                                        <th>운영 시간</th>
                                        <th>관리</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {stores.length > 0 ? (
                                        stores.map((store, index) => (
                                            <tr key={store.id || index}>
                                                <td>{index + 1}</td>
                                                <td>{store.name}</td>
                                                <td>{store.address}</td>
                                                <td>{store.openTime} ~ {store.closeTime}</td>
                                                <td>
                                                    <Button variant="warning" size="sm" className="me-2" onClick={() => handleEditShow(store)}>수정</Button>
                                                    <Button variant="danger" size="sm" onClick={() => handleDelete(store.id)}>삭제</Button>
                                                </td>
                                            </tr>
                                        ))
                                    ) : (
                                        <tr>
                                            <td colSpan="5" className="text-center py-4">등록된 매장이 없습니다.</td>
                                        </tr>
                                    )}
                                </tbody>
                            </Table>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            <Modal show={showEditModal} onHide={() => setShowEditModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>매장 정보 수정</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleUpdateStore}>
                        <Form.Group className="mb-3">
                            <Form.Label>매장명</Form.Label>
                            <Form.Control type="text" name="name" value={editForm.name} onChange={handleEditChange} required />
                        </Form.Group>
                        <Form.Group className="mb-3">
                            <Form.Label>매장 주소</Form.Label>
                            <Form.Control type="text" name="address" value={editForm.address} onChange={handleEditChange} required />
                        </Form.Group>
                        <Row className="mb-3">
                            <Col>
                                <Form.Group>
                                    <Form.Label>오픈 시간</Form.Label>
                                    <Form.Control type="time" name="openTime" value={editForm.openTime} onChange={handleEditChange} required />
                                </Form.Group>
                            </Col>
                            <Col>
                                <Form.Group>
                                    <Form.Label>마감 시간</Form.Label>
                                    <Form.Control type="time" name="closeTime" value={editForm.closeTime} onChange={handleEditChange} required />
                                </Form.Group>
                            </Col>
                        </Row>
                        <div className="d-flex justify-content-end">
                            <Button variant="secondary" className="me-2" onClick={() => setShowEditModal(false)}>취소</Button>
                            <Button variant="primary" type="submit">저장</Button>
                        </div>
                    </Form>
                </Modal.Body>
            </Modal>
        </Container>
    );
}