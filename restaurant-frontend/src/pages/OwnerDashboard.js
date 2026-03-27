import { useState, useEffect } from 'react';
import { Container, Card, Form, Button, Row, Col, Table } from 'react-bootstrap';
import api from '../api/axiosConfig';

export default function OwnerDashboard() {
    const [storeForm, setStoreForm] = useState({ name: '', address: '', openTime: '', closeTime: '' });
    const [stores, setStores] = useState([]);

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

    const handleCreateStore = async (e) => {
        e.preventDefault();
        try {
            const res = await api.post('/stores/create', storeForm);
            alert(`매장 생성이 완료되었습니다! (매장 ID: ${res.data})`);
            setStoreForm({ name: '', address: '', openTime: '', closeTime: '' });

            fetchStores();
        } catch (error) {
            alert('매장 생성에 실패했습니다.');
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
                                    <Button variant="primary" type="submit">
                                        매장 등록하기
                                    </Button>
                                </div>
                            </Form>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            <Row className="justify-content-md-center">
                <Col md={8}>
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
                                            </tr>
                                        ))
                                    ) : (
                                        <tr>
                                            <td colSpan="4" className="text-center py-4">
                                                등록된 매장이 없습니다.
                                            </td>
                                        </tr>
                                    )}
                                </tbody>
                            </Table>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
}